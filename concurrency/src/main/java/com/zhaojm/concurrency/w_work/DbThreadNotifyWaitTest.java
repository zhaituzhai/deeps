package com.zhaojm.concurrency.w_work;

public class DbThreadNotifyWaitTest {

    public static void main(String[] args) {
        DbThreadNotifyWaitTest test = new DbThreadNotifyWaitTest();
        Thread t1 = new Thread(test::printChar);
        Thread t2 = new Thread(test::printNum);
        t1.start();
        t2.start();
    }

    public synchronized void printChar() {
        try {
            for (int i = 97; i < 97 + 26; i++) {
                System.out.println((char) i);
                this.notify();
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void printNum() {
        try {
            for (int i = 0; i < 26; i++) {
                System.out.println(i);
                this.notify();  // 1、先唤醒其他线程，唤醒之后还没有获取锁，于是就阻塞态。3、唤醒线程获取锁，继续运行
                this.wait();  // 2、此线程释放锁
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
