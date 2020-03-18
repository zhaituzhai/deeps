package com.zhaojm.basal.concurrent.share;

public class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;

    // 自增并非一个原子操作
    //    public int next() {
    @Override
    public synchronized int next() {
        ++currentEvenValue; // 这会使这个值处于“不恰当”的状态
        Thread.yield();
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
}
