package com.zhaojm.concurrency.a_base;

/**
 * Java 中的线程中断是一种线程间的协作模式，通过设置线程的中断标志并不能直接终止该线程的执行， 而是被中断的线程根据中断状态自行处理。
 *
 *
 *
 * @author zhaojm
 * @date 2020/4/20 23:02
 */
public class DaThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            // 如果当前线程被中断则退出循环  isInterrupted() 检测当前线程是否被中断，如果是返回true ， 否则返回false 。
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread() + " hello");
            }
        });
        // 启动
        thread.start();
        // 主线程休眠1s，以便于让子线程输出
        Thread.sleep(1000);
        System.out.println("main thread interrupt thread");
        // 中断线程，设置标志仅仅是设置标志， 线程A 实际并没有被中断， 它会继续往下执行。
        thread.interrupt();
        // 等待子线程执行完毕
        thread.join();
        System.out.println("main is over");
    }

    /*
    输出结果
        Thread[Thread-0,5,main] hello
        Thread[Thread-0,5,main] hello
        Thread[Thread-0,5,main] hello
        Thread[Thread-0,5,main] hello
        Thread[Thread-0,5,main] hello
        Thread[Thread-0,5,main] hello
        Thread[Thread-0,5,main] hello
        Thread[Thread-0,5,main] hello
        Thread[Thread-0,5,main] hello
        main thread interrupt thread
        main is over


     */

}
