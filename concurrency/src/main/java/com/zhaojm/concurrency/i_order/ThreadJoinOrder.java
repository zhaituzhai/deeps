package com.zhaojm.concurrency.i_order;

public class ThreadJoinOrder {

    public static void main(String[] args) {

        Thread t2 = new Thread(() -> {
            System.out.println("我是线程2，我想第一个执行");
        });
        Thread t3 = new Thread(() -> {
            try {
                t2.join();
                System.out.println("我是线程3，我想第二个执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t1 = new Thread(() -> {
            try {
                t3.join();
                System.out.println("我是线程1，我想第三个执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();

    }

}
