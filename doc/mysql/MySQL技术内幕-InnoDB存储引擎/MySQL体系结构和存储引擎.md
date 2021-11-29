### 数据库和实例

#### 数据库
物理操作系统文件或其他形式文件类型的集合。在MySQL数据库中，数据库文件可以是frm、MYD、MYI、ibd结尾的文件

#### 实例
MySQL数据库由后台线程以及一个共享内存区组成。共享内存可以被运行的后台线程所共享。数据库实例才是真正用于操作数据库文件的

`启动实例`

可以没有配置文件，MySQL会按照编译时的默认参数设置启动实例
- mysql --help | grep my.cnf 查看在哪些位置查找配置文件
- 如果有多个配置文件，MySQL数据库会以读取到的最后一个配置文件中的参数为准

> show variables like "datadir" 查看数据库所在的路径

#### MySQL的组成部分
- 连接池组件
- 管理服务和工具组件
- SQL接口组件
- 查询分析器组件
- 优化器组件
- 缓冲（Cache）组件
- 插件式存储引擎
- 物理文件

> 存储引擎是基于表的，而不是数据库

#### MySQL存储引擎

`InnoDB`

支持事务，其设计目标主要面向在线事务处理（OLTP）的应用 
- 行锁设计、读取操作不会产生锁
- 多版本并发控制（MVCC）来获得高并发性
- 实现了SQL标准的4种隔离级别，默认为REPEATABLE级别
- 使用一种被称为next-keylocking的策略来避免幻读（phantom）现象的产生
- 提供了插入缓冲（insert buffer）、二次写（double write）、自适应哈希索引（adaptive hash index）、预读（read ahead）

> OLTP（Online Transaction Processing在线事务处理）

`MyISAM`

不支持事务、表锁设计，支持全文索引，主要面向一些OLAP数据库应用
- 它的缓冲池只缓存（cache）索引文件，而不缓冲数据文件
- 表由MYD和MYI组成，MYD用来存放数据文件，MYI用来存放索引文件

`NDB`

一个集群存储引擎,其结构是share nothing的集群架构，因此能提供更高的可用性
- NDB的特点是数据全部放在内存中
- 连接操作（JOIN）是在MySQL数据库层完成的，而不是在存储引擎层完成的

`Memery`

表中的数据存放在内存中
- 使用哈希索引
- 只支持表锁，并发性能较差，并且不支持TEXT和BLOB列类型
- 存储变长字段（varchar）时是按照定常字段（char）的方式进行的，因此会浪费内存

> MySQL数据库使用Memory存储引擎作为临时表来存放查询的中间结果集
> 如果中间结果集大于Memory存储引擎表的容量设置，又或者中间结果含有TEXT或BLOB列类型字段，则MySQL数据库会把其转换到MyISAM存储引擎表而存放到磁盘中

`Archive`

使用行锁来实现高并发的插入操作, 其设计目标主要是提供高速的插入和压缩功能
- 只支持INSERT和SELECT操作，从MySQL 5.1开始支持索引
- 使用zlib算法将数据行（row）进行压缩后存储，压缩比一般可达1∶10

`相关命令`

- show engines 查看MySQL数据库支持的引擎
- 查找information_schema架构下的engines表
- create table myTest Engine=MyISAM

> 演示MySQL各项功能的示例数据库, http：//dev.mysql.com/doc/

#### 链接MySQL
连接到MySQL实例时，MySQL数据库会先检查一张权限视图，用来判断发起请求的客户端IP是否允许连接到MySQL实例
- 该视图在mysql架构下，表名为user

`连接方式`
- TCP/IP套接字方法（mysql -h -u -p）
- 可以使用UNIX域套接字。UNIX域套接字其实不是一个网络协议（在MySQL客户端和数据库实例在一台服务器上的情况下使用）
> show variables "socket" 找到UNIX域套接字路径
> mysql -u -S /tmp/mysql.sock



