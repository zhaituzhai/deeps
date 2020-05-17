package com.zhaojm.jvm.f_heap;

/**
 * -Xms600 -Xmx600m
 *
 * -NewRatio : 设置老年代与新生代的比例。默认2
 *      jps    / jinfo -flag SurvivorRatio 进程id   /  jinfo -flag -NewRatio 进程id
 *
 *  理论上 Eden 空间和两个survivor空间默认比例为 8:1:1；
 *  实际上  可能存在  6:1:1
 *  -XX:SurvivorRatio=8 设置 Eden 区与 s0,s1 比例
 *  -XX:-UseAdaptiveSizePolicy 关闭自适应内存分配策略。
 *  -Xmn：设置新生代大小
 *  Minor GC
 * @author zhaojm
 * @date 2020/5/17 12:13
 */
public class CaHeapSurvivorTest {
    public static void main(String[] args) {
        System.out.println("hello");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
