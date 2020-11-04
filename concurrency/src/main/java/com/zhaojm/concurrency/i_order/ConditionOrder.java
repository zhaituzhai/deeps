package com.zhaojm.concurrency.i_order;

import sun.font.TrueTypeGlyphMapper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionOrder {

    static ReentrantLock lock = new ReentrantLock();
    static Condition c1 = lock.newCondition();
    static Condition c2 = lock.newCondition();

    private static Boolean t1Run = false;
    private static Boolean t2Run = false;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                if (!t1Run) {
                    System.out.println("我是线程1，我想第二个执行，但是线程3还没有执行");
                    c1.await();
                }
                System.out.println("我是线程1，我想第二个执行");
                t2Run = true;
                c2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        });
        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                if (!t1Run) {
                    System.out.println("我是线程2，线程3还每一执行完，我还不能执行");
                    c2.await();
                }
                System.out.println("我是线程2，我想第三个执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        });
        Thread t3 = new Thread(() -> {
            lock.lock();
            System.out.println("我是线程3，我想第一个执行");
            t1Run = true;
            c1.signal();
            lock.unlock();
        });

        t1.start();
        t2.start();
        t3.start();

    }

}
