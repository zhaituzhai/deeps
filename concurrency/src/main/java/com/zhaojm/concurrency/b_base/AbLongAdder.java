package com.zhaojm.concurrency.b_base;

import sun.rmi.runtime.Log;

import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaojm
 * @date 2020-04-29 14:39
 */
public class AbLongAdder {



    public static void main(String[] args) {
        // testPrintFooBar();
        // int[] a = new int[]{1,2,3};
        // int[] b = new int[]{1,2,3};
        // System.out.println();
        // b.equals(a);
        String s = new String();
        s.toUpperCase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("2");
        stringBuilder.substring(0, stringBuilder.length() - 1);

        System.out.println(noBoringZeros(-999999000));
        String[] strs = new String[10];
    }

    public static List<String> myLanguages(final Map<String, Integer> results) {
        // results.entrySet().stream().sorted().map()

        List<String> languages = new ArrayList<>();
        results.forEach((key,value) -> {
            if(value > 60) {
                languages.add(key);
            }
        });
        return languages;
    }

    public static int noBoringZeros(int n) {
        // your code
        /*if (n % 10 != 0)
            return n;
        return noBoringZeros(n / 10);*/
        while(true){
            if (n % 10 != 0)
                return n;
            n /= 10;
        }
    }

    private static void testH2O() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

    }

    private static void testPrintFooBar() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        int n = 5;
        new Thread(() -> {
            try {
                for (int i = 0; i < n; i++) {
                    lock.lock();
                    try {
                        System.out.print("foo");
                        condition.signal();
                        condition.await();
                    } finally {
                        lock.unlock();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                for (int i = 0; i < n; i++) {
                    lock.lock();
                    try {
                        System.out.print("bar");
                        condition.signal();
                        condition.await();
                    } finally {
                        lock.unlock();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }
}
