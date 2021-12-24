### jmap
查看内存中对象信息和JVM堆内存信息

#### 命令格式
jmap [ options ] pid

- -heap 查看堆内存信息
- -histo[:live] 查看堆内对象直方图（对象数量、占用内存大小、类的名称）
- -dump:[live,] format=b, file=filename dump堆信息

[引用文章](https://www.cnblogs.com/z-sm/p/6745375.html)

### Operation not permitted

```text
Error attaching to process: sun.jvm.hotspot.debugger.DebuggerException: Can't attach to the process: ptrace(PTRACE_ATTACH, ..) failed for 12935: Operation not permitted
sun.jvm.hotspot.debugger.DebuggerException: sun.jvm.hotspot.debugger.DebuggerException: Can't attach to the process: ptrace(PTRACE_ATTACH, ..) failed for 12935: Operation not permitted
```

`导致原因`

Linux系统加入了 ptrace-scope 机制. 这种机制为了防止用户访问当前正在运行的进程的内存和状态

[引用文章](https://stackoverflow.com/questions/2913948/jmap-cant-connect-to-make-a-dump/47358522)