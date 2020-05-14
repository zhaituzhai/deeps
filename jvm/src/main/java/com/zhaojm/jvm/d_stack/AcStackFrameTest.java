package com.zhaojm.jvm.d_stack;

/**
 * FILO 先进后出
 *
 * 方法的结束方式分为两种，正常结束，以return 返回，2.抛出未捕获的异常。
 *
 * @author zhaojm
 * @date 2020/5/11 22:59
 */
public class AcStackFrameTest {
    public static void main(String[] args) {
        AcStackFrameTest stackFrameTest = new AcStackFrameTest();
        stackFrameTest.method1();
    }


    private void method1() {
        System.out.println("method1() start...");
        method2();
        System.out.println("method1() end...");
        System.out.println(10/0);
    }

    private int method2() {
        System.out.println("method2() start...");
        int i = 10;
        int m = (int) method3();
        System.out.println("method2() end...");
        return i + m;
    }

    private double method3() {
        System.out.println("method3() start...");
        double j = 20.0;
        System.out.println("method3() end...");
        return j;
    }
}
