package com.zhaojm.concurrency.c_sources;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * JUC 并发包中包含有Atomiclnteger 、AtomicLong 和AtomicBoolean 等原子性操作类
 * AtomicLong 是原子性递增或者递减类，其内部使用Unsafe 来实现，
 * @author zhaojm
 * @date 2020/4/22 23:26
 */
public class BaAtomicLong implements Runnable{

    private AtomicLong i = new AtomicLong(0);
    public long getValue() {
        return i.get();
    }

    private void evenIncrement() {
        i.addAndGet(2);
        i.incrementAndGet();
    }

    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("Exit...");
                System.exit(0);
            }
        }, 5000);
        ExecutorService exec = Executors.newCachedThreadPool();
        BaAtomicLong at = new BaAtomicLong();
        exec.execute(at);
        while (true) {
            long val = at.getValue();
            if(val % 2 != 0){
                System.out.println(val);
                System.exit(0);
            }
        }
    }


    /*
    // AtomicLong 源码
    // 类属性
    // 获取 Unsafe 实例
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    // 存放变量 value 的偏移量
    private static final long valueOffset;
    // 记录基础JVM是否长时间支持无锁compareAndSwap。尽管Unsafe.compareAndSwapLong方法在两种情
    // 况下均有效，但应在Java级别上处理某些构造，以避免锁定用户可见的锁。
    static final boolean VM_SUPPORTS_LONG_CAS = VMSupportsCS8();
    private static native boolean VMSupportsCS8();

    static {
        try {
            // 获取 value 在 AtomicLong 中的偏移量
            valueOffset = unsafe.objectFieldOffset
                (AtomicLong.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    // 实际的变量值
    private volatile long value;


    // 主要方法
    // Atomically increments by one the current value.
    public final long incrementAndGet() {
        return unsafe.getAndAddLong(this, valueOffset, 1L) + 1L;
    }

    // Atomically decrements by one the current value.
    public final long decrementAndGet() {
        return unsafe.getAndAddLong(this, valueOffset, -1L) - 1L;
    }



     */

}
