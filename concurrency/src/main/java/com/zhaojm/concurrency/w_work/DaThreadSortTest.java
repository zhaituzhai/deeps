package com.zhaojm.concurrency.w_work;

public class DaThreadSortTest {

    public static void main(String[] args) {
        testHello();
    }


    public static void testAnother() {

    }

    static boolean flag = true;
    public static void testHello() {
        new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                while (flag) {
                    System.out.println("" + i);
                    flag = false;
                }
            }
        }).start();
        new Thread(() -> {
            new Thread(() -> {
                for (int i = 97; i < 97 + 26; i++) {
                    while (!flag) {
                        System.out.println((char) i);
                        flag = true;
                    }
                }
            }).start();
        }).start();
    }

}
