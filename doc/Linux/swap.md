[引用文章](https://zhuanlan.zhihu.com/p/206863817)
[引用文章](https://blog.csdn.net/zqz_zqz/article/details/80333607)
[引用文章](https://liam.page/2020/07/17/memory-stat-in-TOP/)

### Swap
把不常访问的内存先写到磁盘中，然后释放这些内存，给其他更需要的进程使用。再次访问这些内存时，重新从磁盘读入内存
> 内存不足时，内核线程kswapd0会定期回收内存

#### kswapd0
为了衡量内存的使用情况，kswapd0 定义了三个内存阈值（watermark，也称为水位），
分别是页最小阈值（pages_min）、页低阈值（pages_low）和页高阈值（pages_high）。
剩余内存，则使用 pages_free 表示。

> 剩余内存落在页最小阈值和页低阈值中间，说明内存压力比较大，剩余内存不多了。这时 kswapd0 会执行内存回收，直到剩余内存大于高阈值为止。

cat /proc/zoneinfo
```shell script
Node 0, zone      DMA
  pages free     3853
        min      69
        low      86
        high     103

Node 0, zone    DMA32
  pages free     400410
        min      12549
        low      15686
        high     18823

Node 0, zone   Normal
  pages free     5323
        min      4276
        low      5345
        high     6414
```
> 多个处理器被划分到不同 Node 上，且每个Node 都拥有自己的本地内存空间。
> 而同一个 Node 内部的内存空间，实际上又可以进一步分为不同的内存域（Zone），比如直接内存访问区（DMA）、普通内存区（NORMAL）、伪内存区（MOVABLE）等
