package com.zhaojm.concurrency.d_lock;

import java.util.concurrent.locks.StampedLock;

/**
 *
 * StampedLock 是并发包里面JDK8 版本新增的一个锁，该锁提供了三种模式的读写控
 * 制，当调用获取锁的系列函数时－，会返回一个long 型的变量，我们称之为戳记C stamp),
 * 这个戳记代表了锁的状态。其中t可系列获取锁的函数，当获取锁失败后会返回为0 的
 * stamp 值。当调用释放锁和转换锁的方法时需要传入获取锁时返回的stamp 值。
 *
 * StampedLock 提供的三种读写模式的锁分别如下。
 *     ·写锁writeLock ： 是一个排它锁或者独占锁，某时只有一个线程可以获取该
 *      锁， 当二个线程获取该锁后，其他请求读锁和写锁的线程必须等待，这类似于
 *      ReentrantReadWriteLock 的写锁（不同的是这里的写锁是不可重入锁） ； 当目前没有
 *      线程持有读锁或者写锁时才可以获取到该锁。请求该锁成功后会返回一个stamp 变
 *      量用来表示该锁的版本，当释放该锁时需要调用unlockWrite 方法并传递获取锁时
 *      的stamp 参数。并且它提供了非阻塞的t叩WriteLock 方法。
 *
 *     ·悲观读锁readLock ： 是一个共享锁，在没有线程获取独占写锁的情况下，多个线程
 *      可以同时获取该锁。如果己经有线程持有写锁，则其他线程请求获取该读锁会被阻
 *      塞，这类似于ReentrantReadWriteLock 的读锁（不同的是这里的读锁是不可重入锁〉。
 *      这里说的悲观是指在具体操作数据前其会悲观地认为其他线程可能要对自己操作的
 *      数据进行修改，所以需要先对数据加锁，这是在读少写多的情况下的一种考虑。请
 *      求该锁成功后会返回一个sta mp 变量用来表示该锁的版本，当释放该锁时需要调用
 *      unlockRead 方法并传递stamp 参数。并且它提供了非阻塞的tryReadLock 方法。
 *
 * @author zhaojm
 * @date 2020/4/25 20:57
 */
public class EaStampedLockBase {

    StampedLock lock = new StampedLock();

}

class Point {
    private double x, y;

    private final StampedLock stampedLock = new StampedLock();

    /**
     * 排他锁 -- 写锁
     * @param deltaX
     * @param deltaY
     */
    void move(double deltaX, double deltaY) {
        long stamp = stampedLock.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            stampedLock.unlock(stamp);
        }
    }

    /**
     * 乐观锁
     * 使用乐观读锁还是很容易犯错误的，必须要小心，且必须要保证如下的使用顺序。
     * 1、非阻塞获取版本信息
     * 2、复制变量到线程本地堆栈
     * 3、校验
     * 4、获取读锁
     * 5、复制变量到线程的本地堆栈
     * 6、释放悲观锁
     * @return
     */
    double distanceFromOrigin () {

        // 尝试获取乐观锁
        long stamp = stampedLock.tryOptimisticRead();
        // 将全部变量复制到方法体栈内
        double currentX = x, currentY = y;
        // 检查乐观锁获取了锁戳后，锁有没有被其他写线程排他性抢占
        if (!stampedLock.validate(stamp)) {
            // 如果被抢占则获取一个共享读锁
            stamp = stampedLock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    /**
     * 使用悲观🔒，并尝试转为写锁
     * @param newX
     * @param newY
     */
    void moveIfAtOrigin (double newX, double newY) {
        // 可以使用乐观锁替换
        long stamp = stampedLock.readLock();
        try {
            // 如果当前点在原点移动
            while (x == 0.0 && y == 0.0) {
                // 尝试将获取的读锁升级为写锁
                long ws = stampedLock.tryConvertToWriteLock(stamp);
                // 升级成功，则更新戳记，并设置坐标值，然后退出循环
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    // 读锁升级写锁失败则释放读锁，显示获取独占写锁，然后循环重试
                    stampedLock.unlockRead(stamp);
                    stamp = stampedLock.writeLock();
                }
            }
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    /**
     * StampedLock 提供的读写锁与ReentrantReadWriteLock 类似，只是前者提供的是不可
     * 重入锁。但是前者通过提供乐观读锁在多线程多读的情况下提供了更好的性能， 这是因为
     * 获取乐观读锁时不需要进行CAS 操作设置锁的状态，而只是简单地测试状态。
     */
}
