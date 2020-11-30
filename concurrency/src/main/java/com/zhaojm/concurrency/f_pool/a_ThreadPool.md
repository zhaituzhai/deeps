## 线程池

线程池主要解决两个问题：\
1、执行大量异步线程时线程池能够提供较好的性能，创建与销毁线程时需要开销的，线程池的线程是可复用的。\
2、线程池提供了一种资源限制和管理手段，比如限制线程个数，动态新增线程。\

另外线程池提供可调参数和可扩展性接口，满足不同情景

newCachedThreadPool (线程池线程可用个数可达 Integer.MAX_VALUE)
newFixedThreadPool (固定大小的线程池)
newSingleThreadExecutor (单个线程池，单排队的线程可达 Integer.MAX_VALUE)

Executors 是一个工具类，提供不同线程池实例的静态方法。

ThreadPoolExecutor 继承了 AbstractExecutorService 成员变量 ctl 是一个 integer 的原子变量。
用来记录线程池状态和线程池中线程个数

