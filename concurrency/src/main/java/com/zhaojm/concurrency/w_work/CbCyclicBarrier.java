package com.zhaojm.concurrency.w_work;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier虽说功能与CountDownLatch类似，但是实现原理却完全不同，CyclicBarrier内部使用的是Lock + Condition实现的等待/通知模式。
 * 详情可以查看这个方法的源码：
 * @author zhaojm
 * @date 2020/5/2 23:17
 */
public class CbCyclicBarrier {
    static class PerTaskThread implements Runnable{

        private String task;
        private CyclicBarrier cyclicBarrier;

        public PerTaskThread(String task, CyclicBarrier cyclicBarrier) {
            this.task = task;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(1000));
                    System.out.println(String.format("关卡%d的任务%s完成", i, task));
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                cyclicBarrier.reset();
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () ->{
            System.out.println("本关卡所有任务完成，开始游戏。。。");
        });
        new Thread(new PerTaskThread("加载地图数据1", cyclicBarrier)).start();
        new Thread(new PerTaskThread("加载人物模型1", cyclicBarrier)).start();
        new Thread(new PerTaskThread("加载背景音乐1", cyclicBarrier)).start();
    }
}
