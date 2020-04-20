package com.zhaojm.concurrency.base;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread 类中有一个静态的s leep 方法，当一个执行中的线程调用了Thread 的s leep 方
 * 法后，调用线程会暂时让出指定时间的执行权，也就是在这期间不参与CPU 的调度，但
 * 是该线程所拥有的监视器资源，比如锁还是持有不让出的。指定的睡眠时间到了后该函数
 * 会正常返回，线程就处于就绪状态，然后参与 CPU 的调度，获取到CPU 资源后就可以继
 * 续运行了。如果在睡眠期间其他线程调用了该线程的inten-upt（）方法中断了该线程，则该
 * 线程会在调用s leep 方法的地方抛出IntermptedException 异常而返回。
 * @author zhaojm
 * @date 2020-04-20 18:01
 */
public class CaThreadSleep {
    /* 创建一个独占锁 */
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("child thread1 is in sleep");
                Thread.sleep(10000);
                System.out.println("child thread1 is in awaked");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("child thread2 is in sleep");
                Thread.sleep(10000);
                System.out.println("child thread2 is in awaked");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
    }
    /*

    输出：
        child thread1 is in sleep
        child thread1 is in awaked
        child thread2 is in sleep
        child thread2 is in awaked

    首先，无论你执行多少遍上面的代码都是线程 A 先输出或者线程B 先输出，
    不会出现线程 A 和线程 B 交叉输出的情况。从执行结果来看，线
    程A 先获取了锁，那么线程A 会先输出一行，然后调用sleep 方法让自己睡眠10s ， 在线
    程A 睡眠的这10 s 内那个独占锁lock 还是线程A 自己持有，线程B 会一直阻塞直到线程
    A 醒来后执行 unlock 释放锁。下面再来看一下， 当一个线程处于睡眠状态时，如果另外
    一个线程中断了它， 会不会在调用sleep 方法处抛出异常。

     */

}
