package com.zhaojm.concurrency.c_sources;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAccumul ator 比LongAdder 的功能更强大。例如下面的构造函数， 其中accumulatorFunction
 * 是一个双目运算器接口， 其根据输入的两个参数返回一个计算值， identity 则是LongAccumulator
 * 累加器的初始值。
 *
 * @author zhaojm
 * @date 2020/4/23 22:11
 */
public class BdLongAccumulator {
    public static void main(String[] args) {
        // LongAdder 使用
        LongAdder adder = new LongAdder();
        LongAccumulator accumulator = new LongAccumulator((left, right) -> {
            return left + right;
        }, 0);
    }

}
