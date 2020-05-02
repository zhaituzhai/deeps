package com.zhaojm.concurrency.e_queue;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap在JDK 1.7中，提供了一种粒度更细的加锁机制来实现在多线程下更高的性能，这种机制叫分段锁(Lock Striping)。
 * 优点是：在并发环境下将实现更高的吞吐量，而在单线程环境下只损失非常小的性能。
 * 可以这样理解分段锁，就是将数据分段，对每一段数据分配一把锁。当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问。
 * ConcurrentHashMap是由Segment数组结构和HashEntry数组结构组成。
 * Segment是一种可重入锁ReentrantLock，HashEntry则用于存储键值对数据。
 * 一个ConcurrentHashMap里包含一个Segment数组，Segment的结构和HashMap类似，是一种数组和链表结构， 一个Segment里包含一个HashEntry数组，
 * 每个HashEntry是一个链表结构的元素， 每个Segment守护者一个HashEntry数组里的元素，当对HashEntry数组的数据进行修改时，
 * 必须首先获得它对应的Segment锁。
 *
 * 而在JDK 1.8中，ConcurrentHashMap主要做了两个优化：
 *      同HashMap一样，链表也会在长度达到8的时候转化为红黑树，这样可以提升大量冲突时候的查询效率；
 *      以某个位置的头结点（链表的头结点或红黑树的root结点）为锁，配合自旋+CAS避免不必要的锁开销，进一步提升并发性能。
 *
 * @author zhaojm
 * @date 2020/5/2 10:59
 */
public class FaConcurrentHashMap {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        Hashtable<String, String> hashtable = new Hashtable<>();

        map.put("first","hello world");
        map.put("second", "shark hands");
        map.put("third","good bye");

        System.out.println(map.get("first"));
        System.out.println(map.get("second"));
        System.out.println(map.get("third"));

        System.out.println(map.size());

    }

}
