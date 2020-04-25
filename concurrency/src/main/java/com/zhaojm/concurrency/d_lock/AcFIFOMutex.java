package com.zhaojm.concurrency.d_lock;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * 先进先出的锁
 * @author zhaojm
 * @date 2020/4/24 22:13
 */
public class AcFIFOMutex {
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedDeque<>();

    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        // 只有队首的线程可以获取锁
        // 如果当前线程不是队首或者当前锁已被其他线程获取，则调用park方法挂起自己
        while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
            LockSupport.park(this);
            if(Thread.interrupted()) { // 如果park 方法是因为被中断而返回，则忽略中断，并且重置中断标志
                wasInterrupted = true;
            }
            waiters.remove();
            // 判断标记，如果标记为true 则中断该线程，
            if(wasInterrupted) {
                current.interrupt();
            }
        }
    }

    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }

}
