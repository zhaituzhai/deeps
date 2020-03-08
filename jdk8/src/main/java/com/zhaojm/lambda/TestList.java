package com.zhaojm.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestList {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        for (Integer i : list) {
            System.out.println(i);
        }
        list.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer i) {
                System.out.println(i);
            }
        });

        // lambda
        list.forEach(i -> System.out.println(i));
        // 方法引用
        list.forEach(System.out::println);
        // 构造方法
//        Supplier<Person> supplier1 = () -> new Person();
        Supplier<Person> supplier = Person::new;
        System.out.println(supplier.get().getName());


    }
}
