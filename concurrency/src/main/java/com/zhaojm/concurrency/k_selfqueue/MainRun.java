package com.zhaojm.concurrency.k_selfqueue;

public class MainRun {

    public static void main(String[] args) {
        MyBlockQueue<String> queue = new MyBlockQueue(10);
        Thread t1= new Thread(() -> {
            queue.put("hello1");
            queue.put("hello2");
            queue.put("hello3");
            queue.put("hello4");
            queue.put("hello5");
            queue.put("hello6");
            queue.put("hello7");
            queue.put("hello8");
            queue.put("hello9");
            queue.put("hello10");
            queue.put("hello11");
            queue.put("hello12");
            queue.put("hello13");
            queue.put("hello14");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
            System.out.println("queue.take() = " + queue.take());
        });
        t1.start();
        t2.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(queue.size());
    }

}
