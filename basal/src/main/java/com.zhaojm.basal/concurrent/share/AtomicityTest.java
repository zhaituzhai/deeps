package com.zhaojm.basal.concurrent.share;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicityTest implements Runnable {
    private int i;
    public int getValue(){
        return i;
    }
    public synchronized void evenIncrement(){
        i++;
        i++;
    }
    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicityTest at = new AtomicityTest();
        exec.execute(at);
        while (true) {
            // 要使用 synchronized getValue 才能保证return i 为偶数
            int val = at.getValue();
            if(val % 2 != 0){
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
