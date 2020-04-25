package com.zhaojm.concurrency.e_queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ConcurrentLinkedQueue 是线程安全的无界非阻塞队列，其底层数据结构使用单向链表
 * 实现，对于入队和出队操作使用CAS 来实现线程安全。
 *
 * ConcurrentLinkedQueue 内部的队列使用单向链表方式实现，其中有两个volatile 类型
 * 的Node 节点分别用来存放队列的首、尾节点。从下面的无参构造函数可知，默认头、尾
 * 节点都是指向item 为null 的哨兵节点。新元素会被插入队列末尾，出队时从队列头部获
 * 取一个元素。
 * 在Node 节点内部则维护一个使用volatile 修饰的变量item ，用来存放节点的值； next
 * 用来存放链表的下一个节点，从而链接为一个单向无界链表。其内部则使用UNSafe 工具
 * 类提供的CAS 算法来保证出入队时操作链表的原子性。
 *
 * @author zhaojm
 * @date 2020/4/25 21:27
 */
public class AaConcurrentLinkedQueue {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        // offer：在队尾添加一个元素，不存放 null
        queue.offer("hello");
        // 调用 offer
        queue.add("world");
        // poll poll 操作时在队列头部获取并移除一个元素，如过队列为空返回 null
        queue.poll();
        // peek 获取队列头部一个元素（只获取，不移除）
        queue.peek();
        // size 没有 CAS 锁，多线程可能导致元素计算不精确
        queue.size();
        // remove 如果存在则删除元素，多个则删除第一个，并返回 true
        queue.remove("hello");
        // contains
        queue.contains("hello");

    }


    /*

    ConcurrentLinkedQu eue 的底层使用单向链表数据结构来保存队列元素，每个元素被
    包装成一个Node 节点。队列是靠头、尾节点来维护的，创建队列时头、尾节点指向－个
    item 为null 的哨兵节点。第一次执行peek 或者自rst 操作时会把head 指向第一个真正的队
    列元素。由于使用非阻塞CAS 算法，没有加锁，所以在计算size 时有可能进行了offer 、
    poll 或者remove 操作， 导致计算的元素个数不精确，所以在井发情况下size 函数不是很
    有用。

    入队、出队都是操作使用volatile 修饰的tail 、head 节点，要保证在
    多线程下出入队线程安全，只需要保证这两个Node 操作的可见性和原子性即可。由于
    vo latile 本身可以保证可见性，所以只需要保证对两个变量操作的原子性即可。

    offer 操作是在tail 后面添加元素，也就是调用tail.casNext 方法，而这个方法使用的是
    CAS 操作，只有一个线程会成功，然后失败的线程会循环，重新获取tail ， 再执行casNext
    方法。poll 操作也通过类似CAS 的算法保证出队时移除节点操作的原子性。

     */

}
