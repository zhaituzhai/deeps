package com.zhaojm.jvm.d_stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhaojm
 * @date 2020/5/13 21:45
 */
public class BcForeachTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("hello" + i);
        }

        list.parallelStream().forEach(t -> {
            System.out.println(Thread.currentThread().getName() + t);
        });
    }

    public void testForeach() {
        List<String> stringList = Arrays.asList("hello", "world");
        AtomicInteger a = new AtomicInteger(0);
        stringList.forEach(t -> {
            a.getAndIncrement();
            System.out.println(t);
        });
        System.out.println(a);
    }
}
