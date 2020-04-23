package com.zhaojm.concurrency.c_sources;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * AtomicLong 通过CAS提供了非阻塞的原子操作，相比使用阻塞算法的同步器来说他的性能已经很好了
 * 但是使用AtomicLong 时，在高并发下大量线程会同时去竞争更新同→个原子变量，但是由于同时只有
 * 一个线程的CAS 操作会成功，这就造成了大量线程竞争失败后，会通过无限循环不断进行自旋尝试
 * CAS 的操作， 而这会白白浪费CPU 资源。
 *
 * JDK 8 新增了一个原子性递增或者递减类LongAdder 用来克服在高并发下使用AtomicLong 的缺点。
 *
 * @author zhaojm
 * @date 2020/4/23 21:26
 */
public class BbAtomicLongCount {
    private static AtomicLong atomicLong = new AtomicLong();

    private static Integer[] arrOne = new Integer[]{0,1,2,0,3,4,56,0};
    private static Integer[] arrTwo = new Integer[]{0,2,3,2,1,34,1,0,76};

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            int size = arrOne.length;
            for (int i = 0; i < size; i++) {
                if(arrOne[i].intValue() == 0)
                    atomicLong.incrementAndGet();
            }
        });

        Thread t2 = new Thread(() -> {
            int size = arrTwo.length;
            for (int i = 0; i < size; i++) {
                if(arrTwo[i].intValue() == 0)
                    atomicLong.incrementAndGet();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("count 0 : " + atomicLong);
    }

    /*
    输出结果
    count 0 : 5

     */

}
