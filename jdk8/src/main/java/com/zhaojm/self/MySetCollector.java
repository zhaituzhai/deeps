package com.zhaojm.self;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 自定义收集器  把 list 转换为 set
 * T => 遍历流中的元素类型
 * Set<T> 中间结果容器类型
 * Set<T>  返回的结果类型
 */
public class MySetCollector<T> implements Collector<T, Set<T>, Set<T>> {


    /**
     * 提供一个空的容器供后续方法调用
     * @return
     */
    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked!");
        return HashSet<T>::new;
    }

    /**
     * 累加器类型
     * @return 接收两个参数不返回值
     */
    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked!");
        return Set<T>::add;
    }

    /**
     * 将并行流产生的多个集两两合并
     * @return 合并器
     */
    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked!");
        return (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        };
    }

    /**
     * 最后合并所有结果集，并返回
     * @return
     */
    @Override
    public Function<Set<T>, Set<T>> finisher() {
        System.out.println("combiner invoked!");
//        return t -> t;
        return Function.identity();
    }

    /**
     * 返回当前结果集最多的特性
     * @return
     */
    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("combiner invoked!");
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED));
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "helloworld", "welcome");
        Set set = list.stream().collect(new MySetCollector<>());
        System.out.println(set);
    }

}
