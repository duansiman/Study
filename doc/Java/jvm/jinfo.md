[引用文章](https://segmentfault.com/a/1190000038320962)
[The Parallel Collector 官网文档](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/parallel.html#default_heap_size)

#### 查看JVM运行的参数
> jinfo -flags pid

```text
Attaching to process ID 26471, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.221-b11
Non-default VM flags: -XX:CICompilerCount=2 -XX:InitialHeapSize=2147483648 -XX:LargePageSizeInBytes=134217728 -XX:MaxHeapSize=2147483648 -XX:MaxNewSize=536870912 -XX:MinHeapDeltaBytes=524288 -XX:NewSize=536870912 -XX:OldSize=1610612736 -XX:SurvivorRatio=3 -XX:ThreadStackSize=256 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseFastAccessorMethods -XX:+UseFastUnorderedTimeStamps -XX:+UseParallelGC
Command line:  -Dnacos.logging.path=/data/logs/nacos -javaagent:./skywalkingagent/skywalking-agent.jar -Dspring.profiles.active=pro -Xms2048m -Xmx2048m -Xmn512m -Xss256k -XX:SurvivorRatio=3 -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods
```

> java -XX:+PrintCommandLineFlags -version

```text
-XX:InitialHeapSize=59737920 -XX:MaxHeapSize=955806720 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC
java version "1.8.0_221"
Java(TM) SE Runtime Environment (build 1.8.0_221-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.221-b11, mixed mode)
```

#### 查看具体某一个参数的值
> jinfo -flag ParallelGCThreads pid

```text
-XX:ParallelGCThreads=2
```

#### 查看jvm所有参数默认值

> java -XX:+PrintFlagsInitial -version

#### 查看jvm所有的参数
java -XX:+PrintFlagsFinal -version
> grep HeapSize 过滤堆大小参数

#### 开启/关闭某个JVM参数
开启或者关闭对应名称的参数,主要是针对 boolean 值的参数设置的
> jinfo -flag [+|-]name pid


