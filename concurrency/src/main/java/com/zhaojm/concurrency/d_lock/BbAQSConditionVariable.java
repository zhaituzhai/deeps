package com.zhaojm.concurrency.d_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaojm
 * @date 2020/4/25 10:51
 */
public class BbAQSConditionVariable {

    // 独占锁
    private static ReentrantLock lock = new ReentrantLock();
    // 创建一个 ConditionObject 变量，这个变量就是 Lock 锁对应的一个条件变量。
    // 一个 Lock 对象可以创建多个条件变量
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock(); // 获取锁
            try {
                System.out.println("begin wait");
                condition.await(); // 挂起当前线程，当其他线程调用了 signal
                System.out.println("end wait");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("begin signal");
                condition.signal();
                System.out.println("end signal");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

    }

    /*
    输出结果
    begin wait
    begin signal
    end signal
    end wait

    注意：
    当多个线程同时调用 lock.lock() 方法获取锁是，只有一个线程获取到了锁，其他的线程会被
    转换为 Node 节点插入到 lock 锁对应的 AQS 阻塞队列中，并且自旋 CAS 尝试获取锁。

    如果获取到锁的线程又调用了对应的条件变量的 await() 方法，则该线程会释放获取到的锁，
    并被转换为 Node 节点插入到条件变量对应的条件队列里面去。

    此时应用调用 lock.lock() 方法被阻塞到 AQS 队列里面的一个线程会获取到被释放的锁，
    如果该线程也调用了条件变量的 await() 方法，则该线程也会被放入条件队列中

    当另一个线程调用条件变量的 signal() 或者 signalAll() 方法是，会把条件队列里面的
    一个或者全部 Node 节点移动到 AQS 的阻塞队列中，等待时机获取锁



     */

}
