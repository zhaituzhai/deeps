package com.zhaojm.concurrency.i_order;

import java.util.concurrent.Semaphore;

public class SephmoreOrder {

    private static Semaphore s1 = new Semaphore(1);
    private static Semaphore s2 = new Semaphore(1);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("我是线程1，我想第一个执行");
            s1.release();
        });

        Thread t2 = new Thread(() -> {
            try {
                s1.acquire();
                System.out.println("我是线程2，我想第二个执行");
                s2.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(()-> {
            try {
                s2.acquire();
                t2.join();
                s2.release();
                System.out.println("我是线程3，我想第三个执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t3.start();
        t1.start();
        t2.start();
    }

}
