#### 自增 id 用完了会怎么样
表定义的自增值达到上限后的逻辑是：再申请下一个 id 时，得到的值保持不变

#### InnoDB 系统自增 row_id
创建表没有指定主键，InnoDB会给你创建一个不可见的，长度为 6 个字节的 row_id

`row_id值的来源`

InnoDB 维护了一个全局的 dict_sys.row_id 值，所有无主键的 InnoDB 表，
每插入一行数据，都将当前的 dict_sys.row_id 值作为要插入数据的 row_id，然后把 dict_sys.row_id 的值加 1

`row_id的代码实现`

在代码实现时 row_id 是一个长度为 8 字节的无符号长整型 (bigint unsigned)。
InnoDB 在设计时，给 row_id 留的只是 6 个字节的长度，写到数据表中时只放了最后 6 个字节

> 写入表的 row_id 是从 0 开始到 248-1。达到上限后，下一个值就是 0，然后继续循环

`row id达到上限`

在 InnoDB 逻辑里，申请到 row_id=N 后，就将这行数据写入表中；如果表中已经存在 row_id=N 的行，新写入的行就会覆盖原有的行

> row_id　并不是主键

