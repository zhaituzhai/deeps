package com.zhaojm.concurrency.i_order;

public class ObjectWaitOrder {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    private static Boolean t1Run = false;
    private static Boolean t2Run = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("我是线程1，我想第一个执行");
                t1Run = true;
                lock1.notify();
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock1) {
                try {
                    if (!t1Run) {
                        System.out.println("我是线程2，线程1还没有执行完成，我还不能执行");
                        lock1.wait();
                    }
                    synchronized (lock2) {
                        System.out.println("我是线程2，我想第二个执行");
                        lock2.notify();
                        t2Run = true;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(() -> {
            synchronized (lock2) {
                try {
                    if (!t2Run) {
                        System.out.println("我是线程3，第二个没有执行完成，我还不能执行");
                        lock2.wait();
                    }
                    System.out.println("我是线程3，我想第三个执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t3.start();
        t2.start();
        t1.start();
    }

}
