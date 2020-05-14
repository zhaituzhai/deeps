package com.zhaojm.jvm.d_stack;

/**
 * @author zhaojm
 * @date 2020/5/11 22:24
 */
public class AaStackTest {
    public static void main(String[] args) {
        AaStackTest stackTest = new AaStackTest();
        stackTest.methodA();
    }

    /**
     * 一个栈帧对应一个方法，
     */
    public void methodA() {
        int i = 10;
        int j = 10;
        methodB();
    }

    private void methodB() {
        int k = 30;
        int m = 40;
    }
}
