package com.zhaojm.concurrency.e_queue;

import java.util.concurrent.LinkedBlockingQueue;

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

    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // 添加元素 新增不阻塞
        queue.offer("hello");
        queue.add("world");
        try {
            //
            queue.put("nihao");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 获取
        try {
            // 取
            System.out.println(queue.peek());
            // 延时取移：如果队列中没有则阻塞
            System.out.println(queue.take());
            // 没有则返回 null
            System.out.println(queue.poll());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        queue.remove("nihao");


        System.out.println(queue.size());
    }

    /*
    // 执行 take \ poll 等操作需要获取该锁
    private final ReentrantLock takeLock = new ReentrantLock();

    // 当队列为空时，执行出队操作(比如take)的线程会被放入这个条件队列进行等待
    private final Condition notEmpty = takeLock.newCondition();

    // 执行 put 、 offer 等操作时需要获取该锁
    private final ReentrantLock putLock = new ReentrantLock();

    // 当队列满时， 执行进队操作(比如put)的线程会被放入这个条件队列进行等待
    private final Condition notFull = putLock.newCondition();

    // 当前元素数
    private final AtomicInteger count = new AtomicInteger();

    */

}
