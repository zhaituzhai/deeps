package com.zhaojm.concurrency.base_a;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * 使用synchronized 关键宇的确可以实现线程安全性， 即内存可见性和原子性，但是
 * synchronized 是独占锁，没有获取内部锁的线程会被阻塞掉，而这里的getCount 方法只是
 * 读操作，多个线程同时调用不会存在线程安全问题。但是加了关键宇synchronized 后，同
 * 一时间就只能有一个线程可以调用，这显然大大降低了并发性。你也许会间，既然是只读
 * 操作，那为何不去掉getCount 方法上的sy nchronized 关键字呢？其实是不能去掉的，别忘
 * 了这里要靠synchronized 来实现value 的内存可见性。
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

    /*

    unsafe.compareAndSwapLong(this, valueOffset, expect, update);
    方法： 其中compareAndSwap 的意思是比较并交换。
    CAS 有四个操作数， 分别为： 对象内存位置、对象中的变量的偏移量、变量预期值和新的值。
    其操作含义是， 如果对象obj 中内存偏移量为valueOffset 的变量值为expect ，则使用新的值update 替换旧的值expect 。
    这是处理器提供的一个原子性指令。


     */

}
