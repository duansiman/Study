### 索引组织表
表都是根据主键顺序组织存放的

#### 主键
每张表都有一个主键(Primary Key), 如果不存在

- 看表中是否有非空的唯一索引，如果存在多个，选择建表时第一个定义的非空唯一索引
- 自动创建一个6字节大小的指针

> _rowid 可以显示表的主键，但它只能查看单个列为主键的情况

### 逻辑存储结构
所有的数据都被逻辑地存放在一个空间中，称为表空间，它有段(segment)、区(extent)、页(page)组成

> 段里面有很多区，区里面有很多页，页里面有很多行(row)

#### 表空间
有一个共享表空间ibddata1, 所有数据都存放这个表空间内

- innodb_file_per_table 开启这个参数，每个表的数据单独放一个表空间
- 每个表空间存放只是数据、索引和插入缓冲Bitmap页，其他undo信息、插入缓冲索引页、系统事务信息，二次写缓冲等还是存共享表空间

> show variables like 'innodb_file_per_table'

> 不会在rollback 时去收缩表空间，但会自动判断undo信息是否需要

#### 段
数据段、索引段、回滚段

- 数据段为B+树的叶子节点（Leaf node segment）
- 索引段为B+树的非叶子节点(Non-leaf node segment)

#### 区
由连续的页组成，每个区的大小都为1MB

- 为了连续性，一次从磁盘申请4-5个区
- 页的大小为16KB，一个区中有64个连续的页

`开启innodb_file_per_table，创建的表默认大小是96KB，因为区是64个连续页，怎么不是1MB`
- 每个段开始，先用32个页大小的碎片页（fragment page）来存放数据, 用完才是64个连续页的申请

#### 页
页是磁盘管理的最小单位

`页大小设置`
- key_black_size 设置为2K、4K、8K，页中数据库是压缩
- innodb_page_size 设置4K、8K，页中数据库不是压缩

`页类型`
- 数据页（B-tree Node）
- undo页（undo Log Page）
- 系统页（System Page）
- 事务数据页（Transaction system Page）
- 插入缓冲位图页（Insert Buffer Bitmap）
- 插入缓冲空闲列表页（Insert Buffer Free List）
- 未压缩的二进制大对象页（Uncompressed BLOB Page）
- 压缩的二进制大对象页（compressed BLOB Page）

#### 行
规则每个页最多允许存放16KB-200行的记录，即7992行=8KB-200=8*1024-200

### 行记录格式
提供了Compact 和 Redundant（兼容老版本） 两个格式来存放行记录数据

> show table status like "表名" 查看表使用的行格式，row_format属性表示

#### Compact 格式
存储格式

|变成字段长度列表|NULL标志位|记录头信息|列1数据|列2数据|...|
|----|----|----|----|----|----|

`变成字段长度列表`
- 按照列的顺序逆序放置的
- 列的长度小于255字节，用1字节表示
- 列的长度大于255字节，用2字节表示

`NULL标志位`
- 该行数据中是否有NULL值，有则用1表示（该部分占用字节应该为1字节）
- NULL除了占有NULL标志位，实际存储不占有如何空间

> 00000110 表示第二列和第三列的数据为NULL

`记录头信息`
固定占用5字节

|名称|描述|
|----|----|
|deleted_flag|该行是否已被删除|
|n_owned|该槽拥有的记录数，参考`Page Directory`|
|record_type|记录类型，000:普通 001:B+树节点指针 010:Infimum 011:Supremum 1xx:保留|
|next_record|下一条记录的相对位置（偏移量）|

`列数据`
- 每行数据有两个隐藏列，事务ID列和回滚指针列，分别为6字节和7字节
- 固定长度char字段未能完全占用其长度空间时，会用0x20来进行填充

#### Redundant 格式

#### 行溢出数据
BLOB、LOB这类大对象列，以及varchar数据类型，可能被存放在行溢出数据
- 创建varchar(65535)字段，会报错（65532 不会报错），如果SQL_MODE没设为严格模式，或许可以建立表，但会有警告：把字段类型转成text(show warnings)
- varchar(N) N 指的是字符的长度
- 65535长度是指所有varchar列的长度总和

`溢出存储`
- 页为16KB，即16384字节，怎么存放65532字节，数据默认存放页类型B-tree node中，如果溢出存放Uncompressed BLOB页中
- 发生溢出时，B-tree node 保持前768字节的前缀数据 加上 指向溢出页的偏移量

`什么时候溢出`
- 每个页至少应该有两条行记录（否则树变成链表），如果页只能存放一条记录，自动将行数据存放到溢出页
- varchar类型阈值的长度是8090

> 以上逻辑，BLOB、LOB这类大对象列相似

#### Compressed 和 Dynamic 行记录格式
Compact 和 Redundant 格式称为Antelope文件格式，Compressed和Dynamic格式称为Barracuda文件格式
- 存放BLOB中数据采用完成的行溢出的方式，数据页中只存放20个字节的指针，指向溢出页

#### char的行结构存储
不同的字符集下，char类型列内部存储的可能不是定长的数据
- char_length() 查看字符长度，char() 查看字节长度
- 多字节字符编码的char数据类型的存储，内部将其视为变长字符类型，意味着`变成字段长度列表`会记录char数据类型的长度

#### 页结构
InnoDB数据页由以下7个部分组成
- File Header（文件头）
- Page Header（页头）
- Infimun和Supremum Records
- User Records（用户记录，即行记录）
- Free Space（空闲空间）
- Page Directory（页目录）
- File Trailer（文件结尾信息）

`File Header`
- FIL_PAGE_SPACE_OR_CHKSUM 代表页的checksum值
- FIL_PAGE_OFFSET 表空间中页的偏移量
- FIL_PAGE_PREV 上一页
- FIL_PAGE_NEXT 下一页
- FIL_PAGE_LSN 该页最后被修改的日志序列位置LSN（Log Sequence Number）
- FIL_PAGE_TYPE 页的类型

`Page Header`
- PAGE_N_DIR_SLOTS slots槽数
- PAGE_GARBAGE 已删除记录的字节数，即deleted_flag为1的记录大小的总数
- PAGE_N_RECS 该页中记录的数量

`Infimun和Supremum Records`
数据页中有两个虚拟的行记录，用来限定记录的边界
- Infimum记录是比该页中任何主键值都要小的值，Supremum指比任何可能大的值还要大的值
- 这两个值在页创建时被建立，并且在任何情况下不会被删除

`Page Directory`
存放了记录的相对位置(不是偏移量)，这些记录指针称为Slots（槽）或目录槽（Directory Slots）
- 并不是每个记录拥有一个槽，InnoDB存储引擎的槽是一个稀疏目录（sparse directory），即一个槽中可能包含多个记录
- 在Slots中记录按照索引键值顺序存放，这样可以利用二叉查找迅速找到记录的指针

> B+树索引本身并不能找到具体的一条记录，能找到只是该记录所在的页。数据库把页载入到内存，然后通过Page Directory再进行二叉查找

`File Trailer`
为了检测页是否已经完整地写入磁盘
- File Trailer只有一个FIL_PAGE_END_LSN部分，占用8字节。前4字节代表该页的checksum值，最后4字节和File Header中的FIL_PAGE_LSN相同
- 将这两个值与File Header中的FIL_PAGE_SPACE_OR_CHKSUM和FIL_PAGE_LSN值进行比较
- 通过File Trailer部分进行检测，会有一定的开销。参数innodb_checksums来开启或关闭检查。
- MySQL 5.6.6版本 参数innodb_checksum_algorithm，该参数用来控制检测checksum函数的算法，默认值为crc32