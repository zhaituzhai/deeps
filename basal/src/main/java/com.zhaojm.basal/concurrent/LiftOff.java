package com.zhaojm.basal.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程可以驱动任务
 *
 * Runnable 执行独立的任务，不返回任何值
 *
 */
public class LiftOff implements Runnable {

    protected int countDown = 10;
    private static int taskCount = 0;
    // 区分多个实例的 final修饰 一旦被初始化之后就不希望被修改
    private final int id = taskCount++;

    public LiftOff() {
    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status(){
        return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + "), ";
    }

    /**
     * 当从Runnable导出一个类是，他必须具有run()方法，但是这个方法并无特殊之处--他不会产生任何内在的线程能力，
     * 要实现线程行为，必须显示的将一个任务附着到线程上。
     */
    @Override
    public void run() {
        try {
            while (countDown-- > 0){
                System.out.print(status());
                // Thread.yield()是对 线程调度器（将CPU从一个线程转移到另一个线程）的一种建议
                // 它声明“我已经执行完成，此时正是换给其他任务的时机”
    //            Thread.yield();
//                TimeUnit.MILLISECONDS.sleep(100);
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10));
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted");
        }
    }

    public static void main(String[] args) {
        testExecutors();
    }

    private static void testYield() {
        LiftOff launch = new LiftOff();
        launch.run();  // #0(9), #0(8), #0(7), #0(6), #0(5), #0(4), #0(3), #0(2), #0(1), #0(LiftOff!),
    }

    private static void testBasicThreads() {
        Thread t = new Thread(new LiftOff());
        t.start();
        System.out.println("Waiting for LiftOff");

        // Waiting for LiftOff
        // #0(9), #0(8), #0(7), #0(6), #0(5), #0(4), #0(3), #0(2), #0(1), #0(LiftOff!),
    }

    private static void testMoreBasicThreads() {
        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for LiftOff");

        // Waiting for LiftOff
        // #1(9), #0(9), #2(9), #3(9), #4(9),
        // #1(8), #0(8), #2(8), #4(8), #3(8),
        // #0(7), #2(7), #1(7), #4(7), #3(7),
        // #0(6), #1(6), #2(6), #4(6), #3(6),
        // #0(5), #1(5), #2(5), #4(5), #3(5),
        // #0(4), #1(4), #2(4), #4(4), #3(4),
        // #0(3), #1(3), #2(3), #4(3), #3(3),
        // #0(2), #1(2), #2(2), #4(2), #3(2),
        // #0(1), #1(1), #2(1), #4(1), #3(1),
        // #1(LiftOff!), #0(LiftOff!), #2(LiftOff!), #4(LiftOff!), #3(LiftOff!),
    }

    private static void testExecutors(){
        // 缓存线程执行器  主线程shutdown之后提交的线程在任务完成之后尽快退出
        ExecutorService exec = Executors.newCachedThreadPool();
        // #0(9), #1(9), #0(8), #1(8), #2(9),
        // #1(7), #2(8), #3(9), #0(7), #3(8),
        // #4(9), #2(7), #1(6), #0(6), #4(8),
        // #3(7), #2(6), #1(5), #0(5), #4(7),
        // #3(6), #2(5), #1(4), #0(4), #4(6),
        // #3(5), #2(4), #1(3), #0(3), #3(4),
        // #4(5), #2(3), #1(2), #0(2), #4(4),
        // #2(2), #3(3), #0(1), #1(1), #4(3),
        // #2(1), #3(2), #0(LiftOff!), #4(2),
        // #1(LiftOff!), #2(LiftOff!), #3(1), #4(1), #3(LiftOff!), #4(LiftOff!),

        // sleep
        // #0(9), #1(9), #2(9), #3(9), #4(9),
        // #4(8), #3(8), #2(8), #1(8), #0(8),
        // #4(7), #3(7), #2(7), #1(7), #0(7),
        // #0(6), #4(6), #2(6), #1(6), #3(6),
        // #0(5), #4(5), #2(5), #1(5), #3(5),
        // #0(4), #2(4), #4(4), #1(4), #3(4),
        // #2(3), #0(3), #4(3), #1(3), #3(3),
        // #3(2), #1(2), #4(2), #0(2), #2(2),
        // #2(1), #0(1), #4(1), #1(1), #3(1),
        // #0(LiftOff!), #1(LiftOff!), #3(LiftOff!), #4(LiftOff!), #2(LiftOff!),

        // sleep random 10
        // #0(9), #1(9), #2(9), #3(9), #0(8),
        // #4(9), #2(8), #3(8), #4(8), #4(7),
        // #1(8), #2(7), #3(7), #0(7), #3(6),
        // #4(6), #3(5), #0(6), #2(6), #2(5),
        // #1(7), #0(5), #3(4), #1(6), #1(5),
        // #4(5), #2(4), #1(4), #0(4), #0(3),
        // #3(3), #4(4), #1(3), #2(3), #0(2),
        // #1(2), #4(3), #0(1), #2(2), #3(2),
        // #4(2), #1(1), #0(LiftOff!), #3(1),
        // #3(LiftOff!), #2(1), #4(1), #2(LiftOff!), #1(LiftOff!), #4(LiftOff!),

        // 限定边界的线程执行器
//        ExecutorService exec = Executors.newFixedThreadPool(5);
        // #1(9), #0(9), #1(8), #0(8), #2(9),
        // #3(9), #4(9), #0(7), #2(8), #1(7),
        // #3(8), #4(8), #0(6), #2(7), #1(6),
        // #3(7), #4(7), #2(6), #0(5), #1(5),
        // #3(6), #0(4), #2(5), #4(6), #1(4),
        // #0(3), #2(4), #3(5), #4(5), #1(3),
        // #0(2), #2(3), #4(4), #1(2), #3(4),
        // #0(1), #2(2), #4(3), #1(1), #3(3),
        // #0(LiftOff!), #2(1), #4(2), #1(LiftOff!),
        // #3(2), #2(LiftOff!), #4(1), #3(1), #4(LiftOff!), #3(LiftOff!),

        // 单线程执行器
//        ExecutorService exec = Executors.newSingleThreadExecutor();
        // #0(9), #0(8), #0(7), #0(6), #0(5), #0(4), #0(3), #0(2), #0(1), #0(LiftOff!),
        // #1(9), #1(8), #1(7), #1(6), #1(5), #1(4), #1(3), #1(2), #1(1), #1(LiftOff!),
        // #2(9), #2(8), #2(7), #2(6), #2(5), #2(4), #2(3), #2(2), #2(1), #2(LiftOff!),
        // #3(9), #3(8), #3(7), #3(6), #3(5), #3(4), #3(3), #3(2), #3(1), #3(LiftOff!),
        // #4(9), #4(8), #4(7), #4(6), #4(5), #4(4), #4(3), #4(2), #4(1), #4(LiftOff!),


        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}
