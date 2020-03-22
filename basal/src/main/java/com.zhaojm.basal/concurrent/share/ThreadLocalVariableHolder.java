package com.zhaojm.basal.concurrent.share;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Accessor implements Runnable {
    private final int id;

    public Accessor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    @Override
    public String toString() {
        return "#" + id + ": " +ThreadLocalVariableHolder.get();
    }
}

public class ThreadLocalVariableHolder {
    // ThreadLocal 对象通常被当作静态域存储，在创建ThreadLocal只能通过get/set访问。
    // 其中get() 方法将返回与其线程相关联的对象的副本，而set() 会将参数插入到为其线程存储的对象中，并返回原有对象
    private static  ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
        private Random rand = new Random(47);
        @Override
        protected synchronized Integer initialValue() {
            return rand.nextInt(1000);
        }
    };

    public static void increment() {
        // set() 会将参数插入到为其线程存储的对象中， 并返回存储中原有的对象
        value.set(value.get() + 1);
    }

    public static int get() {
        // get() 方法将返回与其线程相关联的对象副本
        return value.get();
    }

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Accessor(i));
        }
        TimeUnit.SECONDS.sleep(3);
        exec.shutdownNow();
    }
}
