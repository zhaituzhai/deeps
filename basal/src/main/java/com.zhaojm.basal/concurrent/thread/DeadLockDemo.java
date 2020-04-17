package com.zhaojm.basal.concurrent.thread;

/**
 * 死锁
 * 死锁产生的四个条件
 * 1、互斥条件：资源任意时刻只由一个线程占用
 * 2、请求与保持条件：一个进程因请求资源而阻塞时，对以获得的资源保持不放
 * 3、不剥夺条件：线程以获得的资源在未使用完之前不被其他线程强行剥夺，只有自己完毕之后才释放资源。
 * 4、循环等待条件：若干进程之间形成一种头尾相接的循环等待资源的关系
 *
 * 避免线程死锁：破坏上述4中条件
 * 1、破坏互斥条件；
 * 2、破坏请求与保持条件：一次性申请所以资源
 * 3、破坏不剥夺条件：占用部分资源的线程进一步申请其他资源时，如果申请不到，可以主动释放。
 * 4、破坏循环条件：靠按序申请资源来预防。按某一顺序申请资源，释放资源则反序释放。破坏循环等待条件。
 * @author zhaojm
 * @date 2020/4/17 23:11
 */
public class DeadLockDemo {
    private static Object resource1 = new Object();
    private static Object resource2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread() + "get resource1");
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resouce2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "线程1").start();

        new Thread(() -> {
            synchronized (resource2) {
                System.out.println(Thread.currentThread() + "get resource2");
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resouce1");
                synchronized (resource1) {
                    System.out.println(Thread.currentThread() + "get resource1");
                }
            }
        }, "线程2").start();
    }

}
