package com.zhaojm.concurrency.c_sources;

import java.util.concurrent.atomic.LongAdder;

/**
 * JDK 8 新增了一个原子性递增或者递减类LongAdder 用来克服在高并发下使用AtomicLong 的缺点。
 *
 * 既然AtomicLong 的性能瓶颈是由于过多线程同时去竞争一个变量的更新而产生的，那么如果把一个变量
 * 分解为多个变量，让同样多的线程去竞争多个资源，是不是就解决了性能问题？
 * 是的， LongAdder 就是这个思路。
 *
 * 使用LongAdder 时，则是在内部维护多个Ce ll 变量，每个Cell 里面
 * 有一个初始值为0 的long 型变量，这样，在同等并发量的情况下，争夺单个变量更新操
 * 作的线程量会减少，这变相地减少了争夺共享资源的并发量。另外，多个线程在争夺同一
 * 个Cell 原子变量时如果失败了， 它并不是在当前Ce ll 变量上一直自旋CAS 重试，而是尝
 * 试在其他Ce ll 的变量上进行CAS 尝试，这个改变增加了当前线程重试CAS 成功的可能性。
 * 最后，在获取LongAdder 当前值时， 是把所有Cell 变量的value 值累加后再加上base 返回的。
 *
 * 为了解决高并发下多线程对一个变量CAS 争夺失败后进行自旋而造成的降低并发
 * 性能问题， LongAdder 在内部维护多个Cell 元素（一个动态的Ce ll 数组）来分担对单
 * 个变量进行争夺的开销。
 *
 * 1、LongAdder 的结构时怎样的
 * 2、当前线程应该访问Cell 数组里面的哪个Cell元素
 * 3、如何初始化Cell数组
 * 4、Cell数组如何扩容
 * 5、线程访问分配的Cell元素又冲突后如何处理
 * 6、如何保证线程操作被分配的Cell元素的原子性
 *
 * LongAdder 类继承自Striped64 类，在Striped64 内部维护着三个变量。
 * LongAdder 的真实值其实是 base 的值与 Cell 数组里面所有 Cell 元素中的 value 值的累加，
 * base 是个基础值，默认为0 。cellsBusy 用来实现自旋锁，状态值只有0 和l ，当创建Cell 元素，
 * 扩容Ce ll 数组或者初始化Ce ll 数组时，使用CAS 操作该变量来保证同时只有一个线程可
 * 以进行其中之一的操作。Cell 的构造很简单，其内部维护一个被声明为volatile 的变量， 这里声明
 * 为volatile 是因为线程操作value 变量时没有使用锁， 为了保证变量的内存可见性这里将其
 * 声明为volatile 的。另外cas 函数通过CAS 操作，保证了当前线程更新时被分配的Cell 元
 * 素中value 值的原子性。另外， Cell 类使用＠sun.misc.Contended 修饰是为了避免伪共享。
 *
 * @author zhaojm
 * @date 2020/4/23 21:34
 */
public class BcLongAdder {

    private static LongAdder longAdder = new LongAdder();

//    private  static long longAdder = 0L;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    longAdder.increment(); // long adder count 10000
//                    longAdder++;  // long adder count 9761
                    Thread.yield(); //增加线程切换的概率
                }
            }).start();
        }
        Thread.sleep(6000);
        System.out.println("long adder count " + longAdder);
    }

    /*
    本节介绍了JDK 8 中新增的LongAdder 原子性操作类，该类通过内部cells 数组分担
    了高并发下多线程同时对一个原子变量进行更新时的竞争量，让多个线程可以同时对cells
    数组里面的元素进行并行的更新操作。另外，数组元素Cell 使用＠sun . misc .Contended 注
    解进行修饰， 这避免了cells 数组内多个原子变量被放入同一个缓存行，也就是避免了伪
    共享，这对性能也是一个提升。


     */
}
