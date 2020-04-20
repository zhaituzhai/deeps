package com.zhaojm.concurrency.base;

/**
 * 当一个线程调用yield 方法时，实际就是在暗示
 * 线程调度器当前线程请求让出自己的CPU 使用，但是线程调度器可以无条件忽略这个暗示。
 * 我们知道操作系统是为每个线程分配一个时间片来占有CPU 的， 正常情况下当一个
 * 线程把分配给自己的时间片使用完后，线程调度器才会进行下一轮的线程调度，而当一个
 * 线程调用了Thread 类的静态方法 yield 时，是在告诉线程调度器自己占有的时间片中还没
 * 有使用完的部分自己不想使用了，这暗示线程调度器现在就可以进行下一轮的线程调度。
 *
 * 当一个线程调用 yield 方法时， 当前线程会让出CPU 使用权，然后处于就绪状态，线
 * 程调度器会从线程就绪队列里面获取一个线程优先级最高的线程，当然也有可能会调度到
 * 刚刚让出CPU 的那个线程来获取CPU 执行权。
 * @author zhaojm
 * @date 2020/4/20 22:36
 */
public class CbThreadYield implements Runnable {

    public CbThreadYield() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            if( i % 5 == 0 ){
                System.out.println(Thread.currentThread() + "yield cpu...");
                /*

                A hint to the scheduler that the current thread is willing to yield
                its current use of a processor. The scheduler is free to ignore this hint.

                向调度程序提示当前线程愿意放弃当前使用的处理器。调度程序可以随意忽略此提示。
                 */
                Thread.yield();
            }
        }
        System.out.println(Thread.currentThread() + "yield over.");
    }

    public static void main(String[] args) {
        new CbThreadYield();
        new CbThreadYield();
        new CbThreadYield();
    }

    /*

    输出结果：
    注释Thread.yield()
    Thread-0yield cpu...
    Thread-0yield over.
    Thread-1yield cpu...
    Thread-1yield over.
    Thread-2yield cpu...
    Thread-2yield over.
    不注释
    Thread[Thread-1,5,main]yield cpu...
    Thread[Thread-0,5,main]yield cpu...
    Thread[Thread-2,5,main]yield cpu...
    Thread[Thread-1,5,main]yield over.
    Thread[Thread-0,5,main]yield over.
    Thread[Thread-2,5,main]yield over.



     */
}
