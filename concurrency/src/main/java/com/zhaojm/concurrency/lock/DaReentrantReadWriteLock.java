package com.zhaojm.concurrency.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * 读写锁ReentrantReadWriteLock 的原理，它的底层是使用AQS 实现的。
 * ReentrantReadWriteLock 巧妙地使用A QS 的状态值的高16 位表示获取到读锁的个数，低
 * 16 位表示获取写锁的线程的可重入次数，并通过CAS 对其进行操作实现了读写分离，这
 * 在读多写少的场景下比较适用。
 *
 * @author zhaojm
 * @date 2020/4/25 15:49
 */
public class DaReentrantReadWriteLock {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        new Thread(() -> {
            // 写锁 独占锁
            lock.writeLock().lock();
            try {
                System.out.println("insert user(user_id, user_name) values ('1', 'matte')");
            } finally {
                lock.writeLock().unlock();
            }
        }).start();
        /*

    protected final boolean tryAcquire(int acquires) {
        Thread current = Thread.currentThread();
        int c = getState();
        int w = exclusiveCount(c);
        // c != 0 说明读锁或者写锁已经被某线程获取
        if (c != 0) {
            // (Note: if c != 0 and w == 0 then shared count != 0)
            // w == 0 说明已经有线程获取了读锁，w != 0 并且当前线程不是写锁的拥有者，则返回 false
            if (w == 0 || current != getExclusiveOwnerThread())
                return false;
            // 当前线程获取el写锁，判断可重入的次数
            if (w + exclusiveCount(acquires) > MAX_COUNT)
                throw new Error("Maximum lock count exceeded");
            // Reentrant acquire
            // 设置可重入的次数
            setState(c + acquires);
            return true;
        }

        // 第一个写线程获取写锁
        if (writerShouldBlock() || !compareAndSetState(c, c + acquires))
            return false;
        setExclusiveOwnerThread(current);
        return true;
    }
     */

        new Thread(() -> {
            // 读锁使 共享锁
            lock.readLock().lock();
            try {
                System.out.println("select * from user;");
            } finally {
                lock.readLock().unlock();
            }
        }).start();

        /*
        protected final int tryAcquireShared(int unused) {
            // 获取当前状态值
            Thread current = Thread.currentThread();
            int c = getState();
            // 判断写锁是否被占用
            if (exclusiveCount(c) != 0 &&
                    getExclusiveOwnerThread() != current)
                return -1;
            // 获取读锁
            int r = sharedCount(c);
            // 尝试获取锁，多个读线程只有一个不会成功，不成的进入 fullTryAcquireShared 进行重试
            if (!readerShouldBlock() &&
                    r < MAX_COUNT &&
                    compareAndSetState(c, c + SHARED_UNIT)) {
                // 第一个线程获取读锁
                if (r == 0) {
                    firstReader = current;
                    firstReaderHoldCount = 1;
                // 如果当前线程是第一个获取读锁的线程
                } else if (firstReader == current) {
                    firstReaderHoldCount++;
                } else {
                    // 记录最后一个获取读锁的线程或者记录其他线程读锁的可重入性
                    HoldCounter rh = cachedHoldCounter;
                    if (rh == null || rh.tid != getThreadId(current))
                        cachedHoldCounter = rh = readHolds.get();
                    else if (rh.count == 0)
                        readHolds.set(rh);
                    rh.count++;
                }
                return 1;
            }
            // 类似于 tryAcquireShared，但是是自旋获取
            return fullTryAcquireShared(current);
        }
        */
    }





}
