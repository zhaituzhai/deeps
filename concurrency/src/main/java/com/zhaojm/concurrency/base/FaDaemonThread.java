package com.zhaojm.concurrency.base;

/**
 * Java 中的线程分为两类，分别为daemon 线程(守护线程)和user 线程（用户线程）。
 * 在JVM 启动时会调用  main 函数, main 函数所在的钱程就是一个用户线程，其实在JVM
 * 内部同时－还启动了好多守护线程， 比如垃圾回收线程。那么守护线程和用户线程有什么区
 * 别呢？区别之一是当最后一个非守护线程结束时， JVM 会正常退出，而不管当前是否有
 * 守护线程，也就是说守护线程是否结束并不影响 JVM 的退出。言外之意，只要有一个用
 * 户线程还没结束， 正常情况下JVM 就不会退出。
 * @author zhaojm
 * @date 2020/4/20 23:23
 */
public class FaDaemonThread {
    public static void main(String[] args) throws InterruptedException {
        Thread daemonThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("hell0");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
        Thread.sleep(1000);
    }

}
