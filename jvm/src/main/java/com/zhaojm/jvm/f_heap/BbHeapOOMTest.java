package com.zhaojm.jvm.f_heap;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author zhaojm
 * @date 2020/5/17 11:39
 */
public class BbHeapOOMTest {
    public static void main(String[] args) {
        ArrayList<Picture> list = new ArrayList<>();
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new Picture(new Random().nextInt(1024 * 1024)));
        }
    }
}

class Picture {
    private byte[] pixels;

    public Picture(int length) {
        this.pixels = new byte[length];
    }
}
