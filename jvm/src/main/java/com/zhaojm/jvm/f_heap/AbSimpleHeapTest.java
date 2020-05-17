package com.zhaojm.jvm.f_heap;

/**
 * -Xms10M -Xmx10M -XX:+PrintGCDetails
 * @author zhaojm
 * @date 2020/5/17 10:41
 */
public class AbSimpleHeapTest {
    private int id;

    public AbSimpleHeapTest(int id) {
        this.id = id;
    }

    public void show() {
        System.out.println("My id is " + id);
    }

    public static void main(String[] args) {
        // 对象在堆中实例化，栈中存储指向堆的地址。
        // 栈中的局部常量池中存放  test1/test2 的局部变量 指向堆中的实例。
        AbSimpleHeapTest test1 = new AbSimpleHeapTest(2);
        AbSimpleHeapTest test2 = new AbSimpleHeapTest(3);

        // 数组
        int[] arr = new int[10];

        Object[] arr1 = new Object[10];
    }
}
