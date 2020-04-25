package com.zhaojm.concurrency.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 实现线程安全的 list
 *
 * @author zhaojm
 * @date 2020/4/25 15:40
 */
public class CaReentrantLockList {

    public static void main(String[] args) {
        ReentrantLockList list = new ReentrantLockList();
//        List<String> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                list.add(Thread.currentThread().getName());
                Thread.yield();
                System.out.println(list.get(finalI));
                System.out.println(Thread.currentThread().getName());
            }).start();
        }
    }

}

class ReentrantLockList {
    private ArrayList<String> array = new ArrayList<>();
    private volatile ReentrantLock lock = new ReentrantLock();

    public void add(String e) {
        lock.lock();
        try {
            array.add(e);
        } finally {
            lock.unlock();
        }
    }

    public void remove(String e) {
        lock.lock();
        try {
            array.remove(e);
        } finally {
            lock.unlock();
        }
    }

    public String get(int index) {
        lock.lock();
        try {
            return array.get(index);
        } finally {
            lock.unlock();
        }
    }

}
