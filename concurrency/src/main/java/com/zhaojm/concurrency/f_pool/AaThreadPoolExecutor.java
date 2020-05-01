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

}
