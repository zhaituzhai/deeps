package com.zhaojm.lambda;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 高阶函数：一个函数接收一个函数作为参数，或者返回一个函数作为返回值；
 */
public class TestFunction {
    public static void main(String[] args) {

        TestFunction test = new TestFunction();
//        // 传递动作不是具体的值
//        System.out.println(test.compute(3, value -> 2*value));
//        System.out.println(test.compute(4, value -> value*value));
//
//        System.out.println(test.convert(1, value -> String.valueOf(value + "hello world")));


        Function<Integer, Integer> function = value -> value * 2;
        System.out.println(test.compute(2,function));

        // 2*2 *3
        System.out.println(test.compute(2, v -> v*3, v -> v * v));
        // 2*3*(2*3)
        System.out.println(test.compute1(2, v -> v*3, v -> v * v));

        System.out.println(test.compute2(2, 4, (v1, v2) -> v1 + v2));

    }

    public int compute(int a, Function<Integer, Integer> function){
        int result = function.apply(a);
        return result;
    }

    public String convert(int a, Function<Integer, String> function){
        return function.apply(a);
    }

    public int compute(int a, Function<Integer, Integer> func1, Function<Integer, Integer> func2){
        return func1.compose(func2).apply(a);
    }

    public int compute1(int a, Function<Integer, Integer> func1, Function<Integer, Integer> func2){
        return func1.andThen(func2).apply(a);
    }

    public int compute2(int a, int b, BiFunction<Integer, Integer, Integer> biFunction){
        return biFunction.apply(a,b);
    }

    public int compute2(int a, int b, BiFunction<Integer, Integer, Integer> func1, Function<Integer, Integer> func2){
        return func1.andThen(func2).apply(a,b);
    }
}
