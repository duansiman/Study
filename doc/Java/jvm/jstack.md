[引用文章](https://juejin.cn/post/6844904152850497543)

### 命令
jstack [option] <pid>
jstack [ option ] executable core
> java程序崩溃生成core文件

用于生成虚拟机当前时刻的线程快照（线程快照是当前虚拟机内每一条线程正在执行的方法堆栈的集合）

- -F 当正常输出的请求不被响应时，强制输出线程堆栈
- -m 如果调用到本地方法的话，可以显示C/C++的堆栈
- -l 除堆栈外，显示关于锁的附加信息，在发生死锁时可以用jstack -l pid来观察锁持有情况

### 线程状态
runnable，线程处于执行中
deadlock，死锁（重点关注）
blocked，线程被阻塞 （重点关注）
Parked，停止
locked，对象加锁
waiting，线程正在等待
waiting to lock 等待上锁
Object.wait()，对象等待中
waiting for monitor entry 等待获取监视器（重点关注）
Waiting on condition，等待资源（重点关注），最常见的情况是线程在等待网络的读写
