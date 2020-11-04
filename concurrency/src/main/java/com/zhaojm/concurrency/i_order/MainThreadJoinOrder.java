package com.zhaojm.concurrency.i_order;

public class MainThreadJoinOrder {

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

        try {
            t2.start();
            t2.join();
            t3.start();
            t3.join();
            t1.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
