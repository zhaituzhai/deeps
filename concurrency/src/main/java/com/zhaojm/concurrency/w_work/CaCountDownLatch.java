package com.zhaojm.concurrency.w_work;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * CountDown代表计数递减，Latch是“门闩”的意思。也有人把它称为“屏障”。
 * 而CountDownLatch这个类的作用也很贴合这个名字的意义，假设某个线程在执行任务之前，需要等待其它
 * 线程完成一些前置任务，必须等所有的前置任务都完成，才能开始执行本线程的任务。
 *
 * 内部同样是一个基层了AQS的实现类Sync，且实现起来还很简单，可能是JDK里面AQS的子类中最简单的实现了，有兴趣的读者可以去看看这个内部类的源码。
 *
 * 需要注意的是构造器中的计数值（count）实际上就是闭锁需要等待的线程数量。
 * 这个值只能被设置一次，而且CountDownLatch没有提供任何机制去重新设置这个计数值。
 *
 * @author zhaojm
 * @date 2020/5/2 23:05
 */
public class CaCountDownLatch {

    static class PerTaskThread implements Runnable{

        private String task;
        private CountDownLatch countDownLatch;

        public PerTaskThread(String task, CountDownLatch countDownLatch) {
            this.task = task;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                Random random = new Random();
                Thread.sleep(random.nextInt(1000));
                System.out.println(task + " - 任务完成");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(() -> {
            try {
                System.out.println("等待数据加载。。。");
                System.out.println(String.format("还有%d个前置任务", countDownLatch.getCount()));
                countDownLatch.await();
                System.out.println("数据加载完成，正式开始游戏");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(new PerTaskThread("加载地图数据", countDownLatch)).start();
        new Thread(new PerTaskThread("加载人物模型", countDownLatch)).start();
        new Thread(new PerTaskThread("加载背景音乐", countDownLatch)).start();

    }


}
