package com.zhaojm.concurrency.c_sources;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * JUC 并发包中包含有Atomiclnteger 、AtomicLong 和AtomicBoolean 等原子性操作类
 * AtomicLong 是原子性递增或者递减类，其内部使用Unsafe 来实现，
 * @author zhaojm
 * @date 2020/4/22 23:26
 */
public class BaAtomicLong implements Runnable{

    private AtomicLong i = new AtomicLong(0);
    public long getValue() {
        return i.get();
    }

    private void evenIncrement() {
        i.addAndGet(2);
    }

    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("Exit...");
                System.exit(0);
            }
        }, 5000);
        ExecutorService exec = Executors.newCachedThreadPool();
        BaAtomicLong at = new BaAtomicLong();
        exec.execute(at);
        while (true) {
            long val = at.getValue();
            if(val % 2 != 0){
                System.out.println(val);
                System.exit(0);
            }
        }
    }

}
