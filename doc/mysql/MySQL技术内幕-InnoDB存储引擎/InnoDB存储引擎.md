### InnDB体系结构
由后台线程和缓冲池组成

#### 后台线程
多线程的模型

`Master Thread`

主要负责将缓冲池中的数据异步刷新到磁盘，保证数据的一致性，包括脏页的刷新、合并插入缓冲（INSERTBUFFER）、UNDO页的回收等
`IO Thread`

大量使用了AIO（Async IO）来处理写IO请求，这样可以极大提高数据库的性能
- IO Thread的工作主要是负责这些IO请求的回调（callback）处理
- InnoDB 1.0版本之前共有4个IO Thread，分别是write、read、insert buffer和log IO thread
- 从InnoDB 1.0.x版本开始，read thread和write thread分别增大到了4个, 分别使用innodb_read_io_threads和innodb_write_io_threads参数进行设置

> show variables like "innodb_version"
> show variables like "innodb_%io_threads"
> show engine innodb status

`Purge Thread`

事务被提交后，其所使用的undolog可能不再需要，因此需要PurgeThread来回收已经使用并分配的undo页
- 在InnoDB 1.1版本之前，purge操作仅在InnoDB存储引擎的Master Thread中完成
- innodb_purge_threads=1 启动独立的Purge Thread
- 从InnoDB 1.2版本开始，InnoDB支持多个Purge Thread
> show variables like "innodb_purge_threads"

`Page Cleaner Thread`

在InnoDB 1.2.x版本中引入的。其作用是将之前版本中脏页的刷新操作都放入到单独的线程中来完成

#### 内存

`缓冲池`

基于磁盘存储的，并将其中的记录按照页的方式进行管理。通过内存的速度来弥补磁盘速度较慢对数据库性能的影响
- 首先将从磁盘读到的页存放在缓冲池中，这个过程称为将页“FIX”在缓冲池中。下一次再读相同的页时，首先判断该页是否在缓冲池中
- 对于数据库中页的修改操作，则首先修改在缓冲池中的页，然后再以一定的频率刷新到磁盘上
- 页从缓冲池刷新回磁盘的操作并不是在每次页发生更新时触发，而是通过一种称为Checkpoint的机制刷新回磁盘
- 缓冲池的配置通过参数innodb_buffer_pool_size来设置 show variables like "innodb_buffer_pool_size"
> 缓冲池中缓存的数据页类型有：索引页、数据页、undo页、插入缓冲（insert buffer）、自适应哈希索引（adaptive hash index）、InnoDB存储的锁信息（lock info）、数据字典信息（data dictionary）等

**多个缓冲池实例**

每个页根据哈希值平均分配到不同缓冲池实例中, 减少数据库内部的资源竞争，增加数据库的并发处理能力
- innodb_buffer_pool_instances来进行配置，该值默认为1 
- SHOW ENGINE INNODB STATUS 可以看到缓冲池实例情况
- information_schema架构下的表INNODB_BUFFER_POOL_STATS来观察缓冲的状态

`LRU List、Free List和Flush List`
**LRU** 
缓冲池是通过LRU（Latest Recent Used，最近最少使用）算法来进行管理的, 
在InnoDB存储引擎中，缓冲池中页的大小默认为16KB，同样使用LRU算法对缓冲池进行管理
- 在InnoDB的存储引擎中，LRU列表中还加入了midpoint位置
- 新读取到的页，虽然是最新访问的页，但并不是直接放入到LRU列表的首部，而是放入到LRU列表的midpoint位置
- 在默认配置下，该位置在LRU列表长度的5/8处。midpoint位置可由参数innodb_old_blocks_pct控制

> 参数innodb_old_blocks_pct默认值为37，表示新读取的页插入到LRU列表尾端的37%的位置
> 在InnoDB存储引擎中，把midpoint之后的列表称为old列表，之前的列表称为new列表

**为什么引入midpoint 策略**
- 那么某些SQL操作可能会使缓冲池中的页被刷新出，从而影响缓冲池的效率。常见的这类操作为索引或数据的扫描操作
- 这类操作需要访问表中的许多页，甚至是全部的页，而这些页通常来说又仅在这次查询操作中需要，并不是活跃的热点数据
- 参数innodb_old_blocks_time，用于表示页读取到mid位置后需要等待多久才会被加入到LRU列表的热端

**Free List**
- 这时页都存放在Free列表中。当需要从缓冲池中分页时，首先从Free列表中查找是否有可用的空闲页，若有则将该页从Free列表中删除，放入到LRU列表中
- 当页从LRU列表的old部分加入到new部分时，称此时发生的操作为page made young
- 因为innodb_old_blocks_time的设置而导致页没有从old部分移动到new部分的操作称为page not made young

**缓冲相关查询方法**
- 通过命令SHOW ENGINE INNODB STATUS来观察LRU列表及Free列表的使用情况和运行状态
- 通过表INNODB_BUFFER_POOL_STATS来观察缓冲池的运行状态
- 通过表INNODB_BUFFER_PAGE_LRU来观察每个LRU列表中每个页的具体信息
> Database pages表示LRU列表中页的数量
> Modified db pages 显示了脏页的数量
> 缓冲池中的页还可能会被分配给自适应哈希索引、Lock信息、InsertBuffer等页，而这部分页不需要LRU算法进行维护
> Buffer pool hit rate，表示缓冲池的命中率, 通常该值不应该小于95%。若发生Buffer pool hit rate的值小于95%这种情况，用户需要观察是否是由于全表扫描引起的LRU列表被污染的问题

**unzip_LRU**
对于非16KB的页，是通过unzip_LRU列表进行管理的
- 通过information_schema架构下的表INNODB_BUFFER_PAGE_LRU来观察unzip_LRU列表中的页(compressed_size 不等于0)

**Flush LIST**
Flush列表中的页即为脏页列表
- 在LRU列表中的页被修改后，称该页为脏页（dirty page），即缓冲池中的页和磁盘上的页的数据产生了不一致
- 通过CHECKPOINT机制将脏页刷新回磁盘
- 脏页既存在于LRU列表中，也存在于Flush列表中。LRU列表用来管理缓冲池中页的可用性，Flush列表用来管理将页刷新回磁盘，二者互不影响
- 通过information_schema架构下的表INNODB_BUFFER_PAGE_LRU来观察页(OLDEST_MODIFICATION大于0)

`重做日志缓冲`

首先将重做日志信息先放入到这个缓冲区，然后按一定频率将其刷新到重做日志文件
- 重做日志缓冲一般不需要设置得很大，因为一般情况下每一秒钟会将重做日志缓冲刷新到日志文件，因此用户只需要保证每秒产生的事务量在这个缓冲大小之内即可。该值可由配置参数innodb_log_buffer_size控制，默认为8MB

**三种情况下会将重做日志缓冲中的内容刷新到外部磁盘的重做日志文件中**
- Master Thread每一秒将重做日志缓冲刷新到重做日志文件；
- 每个事务提交时会将重做日志缓冲刷新到重做日志文件；
- 当重做日志缓冲池剩余空间小于1/2时，重做日志缓冲刷新到重做日志文件。

`额外的内存池`

在对一些数据结构本身的内存进行分配时，需要从额外的内存池中进行申请，当该区域的内存不够时，会从缓冲池中进行申请
- 分配了缓冲池（innodb_buffer_pool），但是每个缓冲池中的帧缓冲（framebuffer）还有对应的缓冲控制对象（buffer control block），这些对象记录了一些诸如LRU、锁、等待等信息