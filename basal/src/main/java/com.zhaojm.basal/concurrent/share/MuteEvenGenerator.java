package com.zhaojm.basal.concurrent.share;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock 使用
 * Lock 对象必须被显示的创建，锁定，释放
 * 显示的Lock对象在加锁和释放锁方面，相对与内建的synchronized锁来说，还赋予更细粒度的控制力。
 * 这对于专有同步结构是很有用的， 例如用于遍历链接列表中的节点的节节传递的加锁机制（也称锁耦合）
 * 这种遍历代码必须在释放当前节点的锁之前捕捉下一个锁
 */
public class MuteEvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    // 重入锁 互斥调用的锁
    private Lock lock = new ReentrantLock();
    @Override
    public int next() {
        lock.lock();
        try {
            ++currentEvenValue;
            Thread.yield();
            ++currentEvenValue;
            return currentEvenValue;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Generator 添加了一个被互斥调用的锁，并使用lock() 和 unlock() 创建了临界资源。
     * 接着对lock() 的调用，你必须放置在finally子句带有unlock()的try-finally语句中。
     * *注意 return必须在try子句中，确保unlock() 不会过早发生，从而将数据暴露给第二个任务
     *
     * 优点
     * 可以在finally子句将系统维护在正确的状态；
     *
     * 使用synchronized关键字的时候，代码量少，并且用户错误出现的可能性会降低，因此通常只有在解决特殊问题时，才使用显示的Lock对象。
     * 例如，用synchronized关键字不能尝试获取锁且最终获取锁会失败，或者尝试着获取锁一段时间，然后放弃它，
     */

    public static void main(String[] args) {
        EvenChecker.test(new MuteEvenGenerator());
    }
}
