package com.zhaojm.concurrency.w_work;

import java.util.concurrent.Exchanger;

/**
 * Exchanger类用于两个线程交换数据。它支持泛型，也就是说你可以在两个线程之间传送任何数据。
 * @author zhaojm
 * @date 2020/5/2 22:58
 */
public class BaExchanger {

    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                System.out.println("这里是线程A，得到了另一个线程的数据：" + exchanger.exchange("这是来在线程A的数据"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("这时候线程A是阻塞的，在等线程B的数据");
        Thread.sleep(1000);

        new Thread(() -> {
            try {
                System.out.println("这里是线程B，得到了另一个线程的数据：" + exchanger.exchange("这是来在线程B的数据"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    /*

    这时候线程A是阻塞的，在等线程B的数据
    这里是线程B，得到了另一个线程的数据：这是来在线程A的数据
    这里是线程A，得到了另一个线程的数据：这是来在线程B的数据

     */

    /*

    可以看到，当一个线程调用exchange方法后，它是处于阻塞状态的，只有当另一个线程也调用了exchange方法，
    它才会继续向下执行。看源码可以发现它是使用park/unpark来实现等待状态的切换的，但是在使用park/unpark方
    法之前，使用了CAS检查，估计是为了提高性能。

    Exchanger一般用于两个线程之间更方便地在内存中交换数据，因为其支持泛型，所以我们可以传输任何的数据，
    比如IO流或者IO缓存。根据JDK里面的注释的说法，可以总结为一下特性：

        此类提供对外的操作是同步的；
        用于成对出现的线程之间交换数据；
        可以视作双向的同步队列；
        可应用于基因算法、流水线设计等场景。


     */
}
