package com.zhaojm.jvm.f_heap;

/**
 *
 * 测试 TLAB 是否开启: 默认情况开启
 *
 * jinfo -flag UseTLAB
 *  -XX:+UseTALB
 *
 * @author zhaojm
 * @date 2020/5/17 16:46
 */
public class DcTLABTest {
    public static void main(String[] args) {
        System.out.println("test...");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
