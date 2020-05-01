package com.zhaojm.concurrency.e_queue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 带优先级的无界阻塞队列，每次出列都返回优先级最高或者最低的元素；
 * 内部使用平衡二叉树堆实现的，默认使用对象的的 compareTo 方法提供比较规则，可用自定义;
 *
 * PriorityBlockingQueue 队列在内部使用二叉树堆维护元素优先级，使用数组作为元素
 * 存储的数据结构，这个数组是可扩容的。当当前元素个数＞＝最大容量时会通过 CAS 算法
 * 扩容，出队时始终保证出队的元素是堆树的根节点，而不是在队列里面停留时间最长的元
 * 素。使用元素的compareTo 方法提供默认的元素优先级比较规则，用户可以自定义优先级
 * 的比较规则。
 *
 * @author zhaojm
 * @date 2020/5/1 9:05
 */
public class DaPriorityBlockingQueue {

    public static void main(String[] args) {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.offer(2);
        queue.put(4);
        queue.add(6);
        queue.offer(1);

        System.out.println(queue.peek());
        System.out.println(queue.poll());
//        queue.
    }

    /*
    // 数组存放元素
    private transient Object[] queue;
    // 队列元素个数
    private transient int size;
    // 比较器
    private transient Comparator<? super E> comparator;
    // 独占锁控制对象只能有一个线程入队出队操作，noEmpty 条件变量用来实现take方法阻塞模式
    private final ReentrantLock lock;
    private final Condition notEmpty;

     */

    /*
    // 扩容建堆算法

    private static <T> void siftUpComparable(int k, T x, Object[] array) {
        Comparable<? super T> key = (Comparable<? super T>) x;
        // 队列元素个数 > 0 则判断插入的位置，否则直接入队
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = array[parent];
            if (key.compareTo((T) e) >= 0)
                break;
            array[k] = e;
            k = parent;
        }
        array[k] = key;
    }


     */

}
