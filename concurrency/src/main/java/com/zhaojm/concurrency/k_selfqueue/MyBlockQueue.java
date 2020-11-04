package com.zhaojm.concurrency.k_selfqueue;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞队列：FIFO队列，线程安全的
 * {@link java.util.concurrent.LinkedBlockingQueue}
 * @param <E>
 */
public class MyBlockQueue<E> {

    private LinkedList<E> queue = new LinkedList<>();

    private AtomicInteger size = new AtomicInteger(0);

    private final int minSize = 0;
    private final int maxSize;

    public MyBlockQueue() {
        this(10);
    }

    public MyBlockQueue(int size) {
        this.maxSize = size;
    }

    //
    private Object lock = new Object();

    public int size() {
        return size.get();
    }

    public E put(E val) {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + "put 获取锁,开始put ==> " + val);
            while (size.get() >= maxSize) {
                try {
                    lock.wait();
                    System.out.println("队列满载，阻塞put");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.push(val);
            size.incrementAndGet();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.notifyAll();
        }
        return val;
    }

    public E take () {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + "take 获取锁");
            E result;
            while (size.get() == 0) {
                try {
                    System.out.println("队列为空，阻塞take");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            result = queue.poll();
            size.decrementAndGet();
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.notifyAll();
            return result;
        }
    }

    public E peek() {
        return queue.peekLast();
    }
}
