package com.zhaojm.jvm.b_classload;

/**
 * @author zhaojm
 * @date 2020/5/9 23:15
 */
public class AbHelloApp {
    private static int a = 1; // prepare: a = 0 ---> initial : a = 1;

    public static void main(String[] args) {
        System.out.println(a);
    }

}
