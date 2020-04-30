package com.zhaojm.concurrency.e_queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 有边界的
 * @author zhaojm
 * @date 2020-04-30 16:01
 */
public class CaArrayBlockingQueue {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> arrayQueue = new ArrayBlockingQueue<>(10);
        // 插入
        arrayQueue.add("hello");
        try {
            arrayQueue.put("world");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        arrayQueue.offer("nihao");

        // 取值
        System.out.println(arrayQueue.peek());
        System.out.println(arrayQueue.poll());


    }

}
