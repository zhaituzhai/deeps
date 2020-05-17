package com.zhaojm.jvm.f_heap;

/**
 * 工具：Java VisualVM
 * 开启两个实例设置 VM options
 *      -Xms10M -Xmx10M     -Xms  =  堆初始  -Xms  =  堆最大
 *      -Xms20M -Xmx20M
 *      -XX:+PrintGCDetails
 * @author zhaojm
 * @date 2020/5/17 10:24
 */
public class AaHeapDemo {
    public static void main(String[] args) {
        System.out.println("start.....");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end...");
    }
}
