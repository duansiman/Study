[JDK8 默认的GC 收集器](https://blog.csdn.net/weixin_43753797/article/details/106450040)
[The Parallel Collector 官网文档](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/parallel.html#default_heap_size)


#### XX:SurvivorRatio 不生效
在HotSpot VM里，并行系的收集器（UseParallelGC / UseParallelOldGC）默认开启-XX:+UseAdaptiveSizePolicy
这个配置会在每次Minor GC之后对From和To区进行自适应分配大小，而SurvivorRatio使用默认值8，设置成任何非8的数值都会无效