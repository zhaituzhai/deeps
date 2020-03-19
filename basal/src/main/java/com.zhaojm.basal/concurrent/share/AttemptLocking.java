package com.zhaojm.basal.concurrent.share;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();
    public void untimed(){
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean captured = lock.tryLock();
        try {
            System.out.println("tryLock() : " + captured);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    private void time(){
        boolean captured = false;
        try {
            // ReentrantLock 允许你尝试着获取单最终未获取锁，这样如歌其他人已经获取这个锁，那么你就可以决定离开去执行其他的事情
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("tryLock(2, TimeUnit.SECONDS) : " + captured);
        }finally {
            if(captured){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {

        final  AttemptLocking al = new AttemptLocking();
        // 创建一个单独的任务来获取锁；
        new Thread() {
            {setDaemon(true);}

            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();

        al.untimed();
        al.time();

//        Thread.yield();
//        al.untimed();
//        al.time();
        // tryLock() : true
        // tryLock(2, TimeUnit.SECONDS) : true
        // acquired
        // tryLock() : false
        // tryLock(2, TimeUnit.SECONDS) : false
    }
}
