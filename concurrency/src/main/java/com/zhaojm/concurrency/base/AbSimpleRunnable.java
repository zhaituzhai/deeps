package com.zhaojm.concurrency.base;

/**
 * 实现 Runnable 接口，实现 run() 方法
 * @author zhaojm
 * @date 2020-04-20 15:32
 */
public class AbSimpleRunnable implements Runnable {

    @Override
    public String toString() {
        return "" + Thread.currentThread().getId();
    }

    @Override
    public void run() {
        System.out.println(this);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new AbSimpleRunnable()).start();
        }
    }

}
