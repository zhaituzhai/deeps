package com.zhaojm.concurrency.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程之间的通知与等待
 * Java 中的 Object 类是所有类的父类,鉴于继承机制,Java 把所有类都需要的方法放到了Object 类里面，其中就包含通知与等待系列函数。
 * 1、wait():
 *  当一个线程调用一个共享变量的 wait() 方法时，该调用线程会被阻塞挂起，直到发生一下几件事情才返回：
 *   1、其他线程调用改共享变量的 notify() 或 notifyAll() 方法；
 *   2、其他线程调用了该线程的 interrupt() 方法，抛出 InterruptException 返回。
 *   另外，如果调用 wait() 方法之前没有获取该对象的监视器锁，则抛出 IllegalMonitorStateException 异常
 *   获取监视器锁的两种方式：
 *      （1）执行 synchronized 修饰的同步代码块时，使用该共享变量作为参数
 *      （2）调用 该共享变量的方法，使用 synchronized 修饰该方法。
 * @author zhaojm
 * @date 2020-04-20 16:51
 */
public class BaThreadCommunication {

    private static int queueSize = 10;
    private static List<String> queue = new ArrayList<>();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
           synchronized (queue) {
               // 消费队列满，则等待队列空闲
               while (queue.size() == queueSize) {
                   try {
                       System.out.println("生产者等待");
                       queue.wait();
                       System.out.println("生产者完毕");
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }

               System.out.println("添加元素");
               queue.add("hello");
               queue.notify();
           }
        });

        Thread t2 = new Thread(() -> {
           synchronized (queue) {
               // 消费队列为空
               while (queue.size() == 0) {
                   try {
                       System.out.println("消费者等待");
                       queue.wait();
                       System.out.println("消费者完毕");
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }

               System.out.println("消费元素");
               queue.remove(0);
               queue.notify();
           }
        });
    }
    /*

    在如上代码中假如生产者线程A 首先通过 synchronized 获取到了queue 上的锁，那么
    后续所有企图生产元素的线程和消费线程将会在获取该监视器锁的地方被阻塞挂起。线程
    A 获取锁后发现当前队列己满会调用queue.wait() 方法阻塞自己，然后释放获取的 queue
    上的锁，这里考虑下为何要释放该锁？如果不释放，由于其他生产者线程和所有消费者线
    程都己经被阻塞挂起，而线程A 也被挂起，这就处于了死锁状态。这里线程 A 挂起自己
    后释放共享变量上的锁，就是为了打破死锁必要条件之一的持有并等待原则。关于死锁后
    面的章节会讲。线程 A 释放锁后，其他生产者线程和所有消费者线程中会有一个线程获
    取 queue 上的锁进而进入同步块，这就打破了死锁状态。

     */
}
