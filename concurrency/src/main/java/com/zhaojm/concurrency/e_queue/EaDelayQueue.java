package com.zhaojm.concurrency.e_queue;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列
 * DelayQueue 内部使用 PriorityQueue 存放数据，使用 ReentrantLock 实现线程同步。
 * 实现 Delayed 接口，由于每个元素都有一个过期时间;
 *
 * DelayQueue 队列，其内部使用 PriorityQueue 存放数据，使
 * 用 ReentrantLock 实现线程同步。另外队列里面的元素要实现Delayed 接口，其中一个是
 * 获取当前元素到过期时间剩余时间的接口，在出队时判断元素是否过期了，一个是元素之
 * 间比较的接口，因为这是一个有优先级的队列。
 *
 * @author zhaojm
 * @date 2020/5/1 10:31
 */
public class EaDelayQueue {

    public static void main(String[] args) {
        DelayQueue<DateLife> queue = new DelayQueue<>();
        /*
            1、获取独占锁
            2、使用优先级队列入队 PriorityQueue.offer
            3、如果 q.peek() == e 如果队列的第一个元素等于当前元素的话

         */
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            queue.offer(new DateLife("hello" + i, random.nextInt(500)));
        }
        DateLife ele = null;
        try {
            // 循环，如果想避免虚假唤醒，则不能把全部元素打印出来
            for (; ; ) {
                while ((ele = queue.take()) != null){
                    System.out.println(ele);
                    // 出队顺序和delay时间有关，而与创建的任务顺序无关
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
class DateLife implements Delayed {

    private String detail;
    private final long delayTime; // 延期时间
    private final long expire; // 任务时间

    public DateLife(String detail, long delayTime) {
        this.delayTime = delayTime;
        this.detail = detail;
        this.expire = System.currentTimeMillis() + delayTime;

    }

    /**
     * 剩余时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 优先级队列中的优先规则
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DateLife{");
        sb.append("delayTime-").append(delayTime);
        sb.append(", detail-").append(detail);
        sb.append(",expire-").append(expire).append("}");
        return sb.toString();
    }
}
