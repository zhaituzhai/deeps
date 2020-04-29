package com.zhaojm.concurrency.b_base;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * 使用 synchronized 关键宇的确可以实现线程安全性， 即内存可见性和原子性，但是
 * synchronized 是独占锁，没有获取内部锁的线程会被阻塞掉，而这里的 getCount 方法只是
 * 读操作，多个线程同时调用不会存在线程安全问题。但是加了关键字 synchronized 后，同
 * 一时间就只能有一个线程可以调用，这显然大大降低了并发性。你也许会间，既然是只读
 * 操作，那为何不去掉 getCount 方法上的 synchronized 关键字呢？其实是不能去掉的，别忘
 * 了这里要靠 synchronized 来实现 value 的内存可见性。
 *
 * CAS 操作
 *
 *  CAS 即 Compare and Swap ，其是 JDK 提供的非阻塞原子性操作， 它通过硬件保证了比较-更新操作的原子性。
 *
 * @author zhaojm
 * @date 2020/4/21 22:04
 */
public class AaAtomicLong {

    private static AtomicLong count = new AtomicLong(0);

    public long getCount(){
        return count.get();
    }

    public long inc() {
        return count.addAndGet(1);
    }

    public static void main(String[] args) throws InterruptedException {
        // countNumber();
        atomicLongOperating();
    }

    private static void countNumber() throws InterruptedException {
        AaAtomicLong a = new AaAtomicLong();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(() -> {
                while (true) {
                    a.inc();
                    System.out.println(Thread.currentThread() + " -> "+ a.getCount());
                }
            });
            t.setDaemon(true);
            t.start();
        }
        Thread.sleep(1);
        System.out.println(a.getCount());
    }

    public static void atomicLongOperating(){
        AtomicLong count = new AtomicLong(0);
        // 比较并修改，输出原值
        System.out.println(count.getAndSet(1));
        // 最终设置为给定值。 延时的
        count.lazySet(2);
        // 如果当前值是期望值(0)，则以原子方式将该值设置为给定的更新值。修改成功为 true
        System.out.println(count.compareAndSet(0, 2));

        // 自增返回原值
        System.out.println(count.getAndIncrement());
        // 自减返回原值
        System.out.println(count.getAndDecrement());
        System.out.println(count.addAndGet(20L));
        // increment / decrement getAndAddLong

        // 对当前值修改,返回原值
        System.out.println(count.getAndUpdate((long x) -> x + 5L));

        System.out.println(count);

    }

    /*

    unsafe.compareAndSwapLong(this, valueOffset, expect, update);
    方法： 其中compareAndSwap 的意思是比较并交换。
    CAS 有四个操作数， 分别为： 对象内存位置、对象中的变量的偏移量、变量预期值和新的值。
    其操作含义是， 如果对象obj 中内存偏移量为valueOffset 的变量值为expect ，则使用新的值update 替换旧的值expect 。
    这是处理器提供的一个原子性指令。


     */

    /*

    public final void lazySet(long newValue) {
        // 设置 obj 对象中offset偏移地址对应的 long 型 field 的值为 value。
        // 这是一个有延迟的 putLongVolatile 方法，并且不保证值修改对其他线程立刻可见。
        // 只有在变量使用 volatile 修饰并且预计会被意外修改时才使用该方法。
        unsafe.putOrderedLong(this, valueOffset, newValue);
    }

     */

    /*
    // 以原子方式设置为给定值并返回旧值
    public final long getAndSet(long newValue) {
        return unsafe.getAndSetLong(this, valueOffset, newValue);
    }

     */

}
