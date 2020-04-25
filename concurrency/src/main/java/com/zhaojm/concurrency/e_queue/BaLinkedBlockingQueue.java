package com.zhaojm.concurrency.e_queue;

/**
 * 独占锁实现阻塞队列
 *
 * LinkedBlockingQueue 也是使用单向链表实现的，其也有两个
 * Node ，分别用来存放首、尾节点， 并且还有一个初始值为0 的原子变量count，用来记录
 * 队列元素个数。另外还有两个ReentrantLock 的实例，分别用来控制元素入队和出队的原
 * 子性，其中takeLock 用来控制同时只有一个线程可以从队列头获取元素，其他线程必须
 * 等待， putLock 控制同时只能有一个线程可以获取锁，在队列尾部添加元素，其他线程必
 * 须等待。另外， notEmpty 和notFu ll 是条件变量，它们内部都有一个条件队列用来存放进
 * 队和出队时被阻塞的线程，其实这是生产者一消费者模型。如
 *
 * @author zhaojm
 * @date 2020/4/25 21:55
 */
public class BaLinkedBlockingQueue {
}
