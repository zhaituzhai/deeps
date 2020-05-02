package com.zhaojm.concurrency.w_work;

import java.util.concurrent.Semaphore;

/**
 * @author zhaojm
 * @date 2020/5/2 22:34
 */
public class H2O {


    private static Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                semaphore.acquire();
                for (int i = 0; i < 2; i++) {
                    System.out.print("H");
                }
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.print("O");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        System.out.println("down");
    }

}
