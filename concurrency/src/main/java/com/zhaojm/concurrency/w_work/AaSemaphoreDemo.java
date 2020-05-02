package com.zhaojm.concurrency.w_work;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Semaphore内部有一个继承了AQS的同步器Sync，重写了tryAcquireShared方法。在这个方法里，会去尝试获取资源。
 *
 * 如果获取失败（想要的资源数量小于目前已有的资源数量），就会返回一个负数（代表尝试获取资源失败）。
 * 然后当前线程就会进入AQS的等待队列。
 * @author zhaojm
 * @date 2020/5/2 22:43
 */
public class AaSemaphoreDemo {

    static class MyThread implements Runnable {

        private int value;
        private Semaphore semaphore;

        public MyThread(int value, Semaphore semaphore) {
            this.value = value;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(String.format("当前线程%d,还剩%d个资源，还有%d个线程在等待",
                        value, semaphore.availablePermits(), semaphore.getQueueLength()));
                Random random = new Random();
                Thread.sleep(random.nextInt(1000));
                semaphore.release();
                System.out.println(String.format("线程%d释放了资源", value));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(new MyThread(i, semaphore)).start();
        }
    }

}
