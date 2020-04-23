package com.zhaojm.concurrency.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @author zhaojm
 * @date 2020/4/23 23:23
 */
public class AbParkUnpark {
    public static void main(String[] args) throws InterruptedException {
        testParkUnpark1();
    }

    private static  void testParkUnpark2() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("child thread begin park!");
            while (!Thread.currentThread().isInterrupted()) {
                LockSupport.park();
            }
            System.out.println("child thread unpark!");
        });
        thread.start();

        Thread.sleep(1000);

        System.out.println("main thread begin unpark!");

        thread.interrupt();
    }

    private static void testParkUnpark1() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("child thread begin park!");
            LockSupport.park();
            System.out.println("child thread unpark!");
        });
        thread.start();

        Thread.sleep(1000);

        System.out.println("main thread begin unpark!");

        LockSupport.unpark(thread);
    }

    /*

    输出
    child thread begin park!
    main thread begin unpark!
    child thread unpark!



     */
}
