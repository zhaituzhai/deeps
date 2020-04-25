package com.zhaojm.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 * 实现了Lock, Serializable 接口, 底层使用 AQS 实现
 * @author zhaojm
 * @date 2020/4/25 14:55
 */
public class CaReentrantLockBase {

    /**
     * 非公平锁: NonfairSync extend Sync extend AQS
     */
    private static ReentrantLock nonFairSyncLock = new ReentrantLock();

    /**
     * 公平锁: FairSync extend Sync extend AQS
     */
    private static ReentrantLock fairSyncLock = new ReentrantLock(true);

    /*
    NonfairSync 与 FairSync 分别实现了获取锁的非公平与公平策略
     */

    public static void main(String[] args) throws InterruptedException {
        /*
         * 调用该方法获取锁，如果锁没有被其他线程使用并且当前线程之前没有获取过这个锁，则当前线程获取到锁
         * 设置锁的拥有者为当前线程，并设置 AQS 的状态值为 1 ， 然后直接返回。
         * 如果当前已经获取过锁，则把 AQS 的状态值 +1 后返回。
         * 如果其他线程已经获取到了锁，则该线程放入 AQS 队列后阻塞挂起
         *  - 默认非公平
         *  - 如果公平锁 state == 0 会判断之前是否存在队列，如果存在则加入 AQS 队列
         */
        nonFairSyncLock.lock();
        /*
         * 尝试释放锁，如果当前线程持有该锁，则时 AQS 状态值 -1 ，如果状态为 0 ， 则释放该锁。
         * 如果释放之前没有获取锁，则会抛出 IIleaglMonitorStateException 异常
         */
        nonFairSyncLock.unlock();

        /*
         * 对中断响应的锁，
         */
        nonFairSyncLock.lockInterruptibly();

        /*
         * 尝试获取锁，如果当前线程获取锁并返回 true ， 否则返回 false。
         * 不会引起当前线程的阻塞
         */
        nonFairSyncLock.tryLock();
        nonFairSyncLock.tryLock(100, TimeUnit.MICROSECONDS);

    }
}
