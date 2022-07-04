[引用文章](https://www.cnblogs.com/redcreen/archive/2011/05/04/2037057.html)

-Xms 初始堆大小
-Xmx 最大堆大小
-Xmn 年轻代大小

#### -Xss 
每个线程的堆栈大小
> 在相同物理内存下,减小这个值能生成更多的线程

#### -client，-server

这两个参数用于设置虚拟机使用何种运行模式，client模式启动比较快，但运行时性能和内存管理效率不如server模式，通常用于客户端应用程序。相反，server模式启动比client慢，但可获得更高的运行性能。

在 windows上，缺省的虚拟机类型为client模式，如果要使用server模式，就需要在启动虚拟机时加-server参数，以获得更高性能，对服 务器端应用，推荐采用server模式，尤其是多个CPU的系统。在Linux，Solaris上缺省采用server模式。

#### AdaptiveSizePolicy
[引用文章](https://www.jianshu.com/p/7414fd6862c5)