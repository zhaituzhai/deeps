package com.zhaojm.jvm.i_string;

/**
 * -Xms15m -Xmx15m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails
 * @author zhaojm
 * @date 2020/5/25 22:09
 */
public class CaStringGCTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            String.valueOf(i).intern();
        }
    }
}
