package com.zhaojm.concurrency.w_work;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * @author zhaojm
 * @date 2020/5/2 23:38
 */
public class CcPhaser {

    static class PerTaskThread implements Runnable{

        private String task;
        private Phaser phaser;

        public PerTaskThread(String task, Phaser phaser) {
            this.task = task;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                try {
                    if(i >= 2 && "加载新手教程".equals(task)) {
                        continue;
                    }
                    Random random = new Random();
                    Thread.sleep(random.nextInt(1000));
                    System.out.println(String.format("关卡%d, 需要加载%s个模块，当前模块【%s】"
                            , i,  phaser.getRegisteredParties(), task));
                    // 从第二个关卡起，不加载NPC
                    if (i == 1 && "加载新手教程".equals(task)) {
                        System.out.println("下次关卡移除加载【新手教程】模块");
                        phaser.arriveAndDeregister(); // 移除一个模块
                    } else {
                        phaser.arriveAndAwaitAdvance();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Phaser phaser = new Phaser(4) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println(String.format("第%d次关卡准备完成", phase + 1));
                return phase == 3 || registeredParties == 0;
            }
        };

        new Thread(new PerTaskThread("加载地图数据", phaser)).start();
        new Thread(new PerTaskThread("加载人物模型", phaser)).start();
        new Thread(new PerTaskThread("加载背景音乐", phaser)).start();
        new Thread(new PerTaskThread("加载新手教程", phaser)).start();
    }

}
