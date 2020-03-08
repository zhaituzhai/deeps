package com.zhaojm.lambda;

import java.util.function.Predicate;

public class TestPredicate {
    public static void main(String[] args) {

        Predicate<String> predicate = p -> p.length() > 5;
        System.out.println(predicate.test("hello2"));

    }
}
