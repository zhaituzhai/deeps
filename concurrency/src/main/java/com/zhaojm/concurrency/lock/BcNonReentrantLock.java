package com.zhaojm.concurrency.lock;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author zhaojm
 * @date 2020/4/25 11:25
 */

public class BcNonReentrantLock {
    final  static NonReentrantLock LOCK = new NonReentrantLock();
    final static Condition notFull = LOCK.newCondition();
    final static Condition notEmpty = LOCK.newCondition();

    final static Queue<String> QUEUE = new LinkedBlockingDeque<>();
    final static int queueSize = 10;

    public static void main(String[] args) throws InterruptedException {
        // 生产者
        Thread producer = new Thread(() -> {
            LOCK.lock();
            try {
                while (QUEUE.size() == queueSize){
                    notEmpty.await();
                }
                QUEUE.add("ele");
                notFull.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        });

        Thread consumer = new Thread(() -> {
            LOCK.lock();
            try {
                while (0 == QUEUE.size()) {
                    notFull.await();
                }
                String ele = QUEUE.poll();
                System.out.println(Thread.currentThread() + "" + ele);
                notEmpty.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        });

        consumer.start();
        producer.start();

    }

}

/**
 * 自定义不可重入锁
 */
class NonReentrantLock implements Lock, java.io.Serializable {

    /**
     * 与 AbstractQueuedSynchronizer 为组合关系
     */
    private  static class Sync extends AbstractQueuedSynchronizer {
        /**
         * 独占锁还是共享锁
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 尝试获取锁
         * @param acquires
         * @return
         */
        @Override
        protected boolean tryAcquire(int acquires) {
            assert acquires == 1;
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 尝试释放锁
         * @param releases
         * @return
         */
        @Override
        protected boolean tryRelease(int releases) {
            assert releases == 1;
            if(getState() == 0)
                throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 获取条件变量的接口
         * @return
         */
        Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final  Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
