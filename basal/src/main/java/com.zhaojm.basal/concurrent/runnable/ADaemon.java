package com.zhaojm.basal.concurrent.runnable;

import java.util.concurrent.TimeUnit;

public class ADaemon implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("start ADaemon");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Exiting via InterruptedException");
        } finally {
            System.out.println("This should always run?");
        }
    }

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new ADaemon());
        // 如果setDaemon(true) finally则不会输出
        // output
        // start ADaemon
        // t.setDaemon(true);

        // 不设置为后台程序
        // output
        // start ADaemon
        // This should always run?
        t.start();
        TimeUnit.MILLISECONDS.sleep(100);
    }
}
