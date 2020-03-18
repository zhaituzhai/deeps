package com.zhaojm.basal.concurrent.runnable;

import java.util.concurrent.*;

public class SimpleDaemons implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " "+this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // 使用线程池
        ExecutorService exec = Executors.newCachedThreadPool(new DaemonThreadFactory(Thread.NORM_PRIORITY));
        // ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        for (int i = 0; i < 10; i++) {
            // Thread daemon = new Thread(new SimpleDaemons());
            // //必须在线程启动前调用setDaemon() 才能把他设置为后台线程
            // daemon.setDaemon(true);

            // 使用工厂定制线程
            // Thread daemon = new DaemonThreadFactory().newThread(new SimpleDaemons());
            // daemon.start();

            exec.execute(new SimpleDaemons());


        }
        System.out.println("All Daemons started!");
        TimeUnit.MILLISECONDS.sleep(500);
        exec.shutdown();
    }
}
