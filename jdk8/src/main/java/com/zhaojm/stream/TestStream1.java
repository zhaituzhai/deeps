package com.zhaojm.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 并发流和串行流
 */
public class TestStream1 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(50000);
        for (int i = 0; i < list.size(); i++) {
            list.add(UUID.randomUUID().toString());
        }
        System.out.println("开始排序");
        long start = System.nanoTime();
//        list.stream().sorted().count(); // 排序耗时：307

//        list.parallelStream().sorted().count();  // 多个线程 排序耗时：192


        long end = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(end - start);
        System.out.println("排序耗时：" + millis);

        // 流存在短路运算的

        List<String> list1 = Arrays.asList("hello", "world", "hello world");
        list1.stream().mapToInt(item -> {
            int len = item.length();
            System.out.println(item);
            return len;
        }).filter(len -> len == 5).findFirst().ifPresent(System.out::println);



        List<String> list2 = Arrays.asList("hello", "hi");
        List<String> list3 = Arrays.asList("zhaojm","tom");
        list2.stream().flatMap(item -> list3.stream().map(item2 -> item + " " + item2))
                .collect(Collectors.toList()).forEach(System.out::println);




    }


}
