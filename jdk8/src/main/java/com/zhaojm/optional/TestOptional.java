package com.zhaojm.optional;

import java.util.Optional;

/**
 * 空指针
 */
public class TestOptional {

    public static void main(String[] args) {
        Optional<String> optional = Optional.of("hello");
//        if(optional.isPresent()){
//            System.out.println(optional.get());
//        }
//        optional.isPresent(i -> System.out.println(i));
        optional.ifPresent(System.out::println);
    }

}
