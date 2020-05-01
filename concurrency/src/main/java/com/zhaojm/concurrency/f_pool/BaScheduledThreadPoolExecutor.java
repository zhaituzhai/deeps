package com.zhaojm.concurrency.f_pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 可以指定一定延迟时间后或者定时进行任务调度执行的线程池
 *
 * ScheduledThreadPoo!Executor 的实现原理，其内部使用 DelayQueue 来存放具体任务。
 * 任务分为三种， 其中一次性执行任务执行完毕就结束了，fixed- delay 任务保证同一个
 * 任务在多次执行之间间隔固定时间， fixed-rate 任务保证按照固定的频率执行。
 * 任务类型使用period 的值来区分。
 *
 * @author zhaojm
 * @date 2020/5/1 23:14
 */
public class BaScheduledThreadPoolExecutor {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.execute(() ->{
            for (int i = 0; i < 5; i++) {
                System.out.println("---");
            }
        });
    }

}
