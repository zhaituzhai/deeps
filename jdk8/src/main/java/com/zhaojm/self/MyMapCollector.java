package com.zhaojm.self;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 输入：set<String>
 * 输出： Map<String, String>
 *
 * 实例输入 ["hello","hi","aloha"]
 * 输出 [{hello, hello},{hello, hello},{hello, hello}]
 */
public class MyMapCollector<T> implements Collector<T, Set<T>, Map<T,T>> {
    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked!");
        // 并行时每个线程会初始化一个容器
        return HashSet<T>::new;
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked!");
        return (set, item) -> {
            // 遍历set时会互相干扰
            System.out.println("accumulator: "+ set + "," +Thread.currentThread().getName());
            set.add(item);
        };
//        return Set<T>::add;
    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked!");
        // 只有一个结果流，则不会调用合并
        return (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        };

    }

    @Override
    public Function<Set<T>, Map<T, T>> finisher() {
        System.out.println("finisher invoked!");
        return set -> {
            Map<T, T> map = new HashMap<>();
            set.stream().forEach(item -> map.put(item, item));
            return map;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked!");
        // CONCURRENT 多个线程操作同一个结果容器  // 并行修改异常 ConcurrentModificationException
//        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED, Characteristics.CONCURRENT));
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            List<String> list = Arrays.asList("hell", "s2", "s3", "s4", "s5", "s6", "sb");
            Set<String> set = new HashSet<>();
            set.addAll(list);
//            Map<String, String> map = set.parallelStream().collect(new MyMapCollector<>());
            // 取决于最后一次时并行还是串行，以最后一个为准
            Map<String, String> map = set.stream().sequential().parallel().collect(new MyMapCollector<>());
            System.out.println(map);
        }
    }
}
