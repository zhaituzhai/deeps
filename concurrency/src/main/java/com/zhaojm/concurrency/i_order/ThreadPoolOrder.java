package com.zhaojm.concurrency.i_order;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolOrder {
    static ExecutorService exec = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("我是线程1，我想第三个执行");
        });
        Thread t2 = new Thread(() -> {
            System.out.println("我是线程2，我想第一个执行");
        });
        Thread t3 = new Thread(() -> {
            System.out.println("我是线程3，我想第二个执行");
        });

        exec.submit(t2);
        exec.submit(t3);
        exec.submit(t1);
    }
}
