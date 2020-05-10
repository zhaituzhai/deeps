package com.zhaojm.jvm.b_classload;

/**
 * @author zhaojm
 * @date 2020/5/10 9:44
 */
public class AcClassInitTest {

    //<clinit> 前置初始化，与静态初始
    private static int num = 1;

    static {
        num = 2;
        number = 20;
        System.out.println(num);
//        System.out.println(number);  // 非法的前置引用
    }

    private static int number = 10; // linking 之 prepare： number = 0 -> initial : 20 --> 10

    public static void main(String[] args) {
        System.out.println(AcClassInitTest.num);  // 2
        System.out.println(AcClassInitTest.number);  // 10
    }

}
