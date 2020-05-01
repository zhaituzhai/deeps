package com.zhaojm.concurrency.g_synchronizer;

import java.util.concurrent.CountDownLatch;

/**
 * 本节首先介绍了Co untDownLatch 的使用，相比使用jo in 方法来实现线程间同步，
 * 前者更具有灵活性和方便性。另外还介绍了CountDownLatch 的原理， CountDownLatch
 * 是使用AQS 实现的。使用AQS 的状态变量来存放计数器的值。首先在初始化
 * CountDownLatch 时设置状态值（计数器值），当多个线程调用countdown 方法时实际是原
 * 子性递减AQS 的状态值。当线程调用await 方法后当前线程会被放入AQS 的阻塞队列等
 * 待计数器为0 再返回。其他线程调用countdown 方法让计数器值递减l ，当计数器值变为
 * 0 时， 当前线程还要调用AQS 的doReleaseShared 方法来激活由于调用await（） 方法而被阻
 * 塞的线程。
 *
 * @author zhaojm
 * @date 2020/5/1 23:25
 */
public class AaCountDownLatch {

    private static volatile CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
            System.out.println("child threadOne over!");
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
            System.out.println("child threadTwo over!");
        });
        t1.start();
        t2.start();

        System.out.println("wait all chile thread over!");
        countDownLatch.await();
        System.out.println("all child thread over!");

    }

    /*
    CountDownLatch 使用 AQS 实现。
    实际上是把计数器的值赋给了 AQS 的状态变量 state。

    TODO await
    线程调用 await() 会被阻塞，直到下面情况才返回；
        1、所有线程都调用了CountDownLatch 对象的 countDown 方法后， 也就是计数器为0时，其他
        线程调用了 interrupt() 方法中断了当前线程就会抛出 异常返回

    TODO countDown()
    计数器值递减

     */

}
