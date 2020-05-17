package com.zhaojm.jvm.f_heap;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author zhaojm
 * @date 2020/5/17 14:16
 */
public class DaHeapInstanceTest {
    byte[] buffer = new byte[new Random().nextInt(200 * 1024)];

    public static void main(String[] args) {
        ArrayList<DaHeapInstanceTest> list = new ArrayList<>();
        while (true) {
            list.add(new DaHeapInstanceTest());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
