[引用文章](https://www.cnblogs.com/ggjucheng/archive/2012/01/08/2316399.html）
### 内容说明
top - 08:46:15 up 880 days, 20:50,  2 users,  load average: 0.43, 0.38, 0.36
> 08:46:15 系统当前时间
> up 880 days, 20:50 系统运行时间，格式为时:分
> 2 users 当前登录用户数
> load average: 0.43, 0.38, 0.36 系统负载，即任务队列的平均长度。三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值

Tasks: 173 total,   1 running, 169 sleeping,   3 stopped,   0 zombie
> 173 total 进程总数、1 running 正在运行的进程数、 169 sleeping 睡眠的进程数、3 stopped 停止的进程数、0 zombie 僵尸进程数

%Cpu(s):  4.9 us,  1.4 sy,  0.0 ni, 93.7 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
> ni 用户进程空间内改变过优先级的进程占用CPU百分比
> wa 等待输入输出的CPU时间百分比
> hi 硬件CPU中断占用百分比
> si 软中断占用百分比
> st 虚拟机占用百分比

KiB Mem : 32779816 total,   964352 free, 26171596 used,  5643868 buff/cache
KiB Swap:        0 total,        0 free,        0 used.  6203388 avail Mem
> 单位KB

PID USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+ COMMAND  
> PR      优先级
> NI      nice值。负值表示高优先级，正值表示低优先级
> VIRT    进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
> RES     进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
> SHR     共享内存大小，单位kb
> S       进程状态(D=不可中断的睡眠状态,R=运行,S=睡眠,T=跟踪/停止,Z=僵尸进程)
> P       最后使用的CPU，仅在多CPU环境下有意义

### 使用技巧
- 按f 可以查看列信息，选择列显示或隐藏，或者排序
- 按c 切换显示命令名称和完整命令行
- M 根据驻留内存大小进行排序。 
- P 根据CPU使用百分比大小进行排序。 
- T 根据时间/累计时间进行排序。 
- W 将当前设置写入~/.toprc文件中。这是写top配置文件的推荐方法
- Ctrl+L 擦除并且重写屏幕

### 命令
top [-] [d] [p] [q] [c] [C] [S] [s]  [n]
- p 通过指定监控进程ID来仅仅监控某个进程的状态
- s 使top命令在安全模式中运行。这将去除交互命令所带来的潜在危险
- c 显示整个命令行而不只是显示命令名 
- i 使top不显示任何闲置或者僵死进程。 