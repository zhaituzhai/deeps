package com.zhaojm.jvm.d_stack;

/**
 * StackOverflowError 栈溢出
 *
 * -Xss 设置栈的大小
 *
 * 默认情况下 count =  11410
 * 设置栈的大小 -Xss 256k : count = 2471
 *
 * @author zhaojm
 * @date 2020/5/11 22:42
 */
public class AbStackErrorTest {
    private static int count = 1;
    public static void main(String[] args) {
        System.out.println(count);
        count++;
        main(args);
    }
}
