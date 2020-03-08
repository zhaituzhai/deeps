package com.zhaojm.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TestLambda1 {
    public static void main(String[] args) {
//        MyInterface inte = () -> {};
//        System.out.println(inte.getClass().getInterfaces()[0]);
//        MyInterface1 inte1 = () -> {};
//        System.out.println(inte1.getClass().getInterfaces()[0]);
//        // () -> {} 必须有上下文
//        // Java 中，Lambda表达式是对象，必须依附于一类特别的对象类型-函数式接口
//        new Thread(() -> {
//            System.out.println("hello world");
//        }).start();


        List<String> strList = Arrays.asList("hello", "world", "hello world");
//        strList.forEach(item -> System.out.println(item.toUpperCase()));
        List<String> strList2 = new ArrayList<>();
//        strList.forEach(item -> strList2.add(item.toUpperCase()));
//        strList2.forEach(System.out::println);

//        Stream  流
//        strList.stream().map(item -> item.toUpperCase()).forEach(item-> System.out.println(item));
        strList.stream().map(String::toUpperCase).forEach(System.out::println);

        Function<String, String> function = String::toUpperCase;
        System.out.println(function.getClass().getInterfaces()[0]);
    }
}

@FunctionalInterface
interface MyInterface{
    void myMethod();
}

@FunctionalInterface
interface MyInterface1{
    void myMethod();
}