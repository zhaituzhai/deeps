package com.zhaojm.concurrency.f_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaojm
 * @date 2020/4/25 22:18
 */
public class AaThreadPoolExecutor {

    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        /*
         * 1、如果任务为null，异常
         * 2、获取当前线程池状态 + 线程个数变量的组合
         * 3、当前线程池中线程个数是否小于 corePoolSize，小于则开启新线程运行
         * 4、如果线程池处于 Running 状态，则添加任务到阻塞队列
         * 4.1、二次检查
         * 4.2、如果当前线程池状态不是 Running 则从队列中删除任务，并执行拒绝策略
         * 4.3、否则如果当前线程池为空，则添加一个线程
         * 5、如果队列满，则新增线程，新增失败则执行拒绝策略dang'ji
         */
        executor.execute(() -> {
            System.out.println("hello thread pool!");
        });
    }

    /*
    ThreadPoolExecutor extends AbstractExecutorService
    // 记录线程池状态与线程个数，使用 ctl 高3位存储线程池状态，低位存储线程个数
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    // runState is stored in the high-order bits
    // 高 3 位存储线程池状态信息
    // running 接受新任务并处理阻塞队列中的任务
    private static final int RUNNING    = -1 << COUNT_BITS;
    // 拒绝新任务，但是处理阻塞任务
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    // 拒绝新任务，抛弃阻塞队列里的任务，中断正在处理的任务
    private static final int STOP       =  1 << COUNT_BITS;
    // 所有任务执行完（）包括阻塞队列，后当前线程活动线程为0，将要调用 terminated 方法。
    private static final int TIDYING    =  2 << COUNT_BITS;
    // 终止状态。terminated 方法调用完成以后的状态
    private static final int TERMINATED =  3 << COUNT_BITS;

    running -> shutdown => 调用 shutdown() 方法，或者隐式调用 finalize() 方法里面的 shutdown() 方法。
    running / stop -> stop 调用 shutdownNow() 方法时
    shutdown -> tidying 当线程池和任务队列都为空时。
    tidying -> terminated 当 terminated() hook 方法执行完成时。

    线程池参数：
    corePoolSize：线程池核心线程个数。
    workQueue：用于保存等待线程执行的任务的阻塞队列：
        ArrayBlockingQueue: 基于数组的有界阻塞队列
        LinkedBlockingQueue: 链表无界阻塞队列
        SynchronousQueue: 只有一个元素的同步队列
        PriorityBlockingQueue: 优先队列
    maximumPoolSize：线程池最大线程数
    threadFactory：创建线程的工厂
    RejectedExecutionHandler：饱和策略，当队列满并且线程个数达到 maximunPoolSize 后采取的策略
    keepAliveTime：存活时间。如果当前线程池中的线程数比核心线程数量多，并且时闲置状态，则这些闲置
        的线程存活的最大时间。
    TimeUnit：存活时间单位


    // 独占锁，控制新增 worker 线程的原子操作
    private final ReentrantLock mainLock = new ReentrantLock();

    Worker 继承 AQS 和 Runnable 接口，是具有承载任务的对象。 Worker 继承了 AQS, 自己实现
    不可重入独占锁，其中 state = 0

    DefaultThreadFactory 线程工厂， newThread 对线程的修饰

     */

    /*
    TODO execute

    public void execute(Runnable command) {
        // 空指针
        if (command == null)
            throw new NullPointerException();
        // 当前线程池的状态+线程个数组合
        int c = ctl.get();
        // 线程池线程个数小于 核心数，小于则开启
        if (workerCountOf(c) < corePoolSize) {
            // 增加执行数，与队列
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        // 如果状态为 running 则添加任务到阻塞队列
        if (isRunning(c) && workQueue.offer(command)) {
            // 二次检查
            int recheck = ctl.get();
            // 如果当前线程池不是 running 则从队列中删除任务，并执行拒绝策略
            if (! isRunning(recheck) && remove(command))
                reject(command);
            // 如果当前线程池为空，则添加一个线程
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        // 如果队满，则新增线程，新增失败则执行拒绝策略
        else if (!addWorker(command, false))
            reject(command);
    }




     */

}
