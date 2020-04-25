package com.zhaojm.concurrency.a_base;

/**
 * @author zhaojm
 * @date 2020-04-20 17:33
 */
public class BbThreadCommunication {
    private static volatile Object object1 = new Object();
    private static volatile Object object2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread (() -> {
            try {
                synchronized (object1) {
                    System.out.println("thread1 get object1 lock");
                    synchronized (object2) {
                        System.out.println("thread1 get object2 lock");
                        System.out.println("thread1 release object1 lock");
                        object1.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread (() -> {
            try {
                Thread.sleep(1000);
                synchronized (object1) {
                    System.out.println("thread1 get object1 lock");
                    System.out.println("thread2 try get object2 lock");
                    synchronized (object2) {
                        System.out.println("thread1 get object2 lock");
                        System.out.println("thread1 release object1 lock");
                        object1.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Main over!");

    }

    /*

    输出结果：
        thread1 get object1 lock
        thread1 get object2 lock
        thread1 release object1 lock
        thread1 get object1 lock
        thread2 try get object2 lock

    这就证明了当线程调用共享对象的 wait() 方法时，当前线程只会释放当前共享对象的
    锁，当前线程持有的其他共享对象的监视器锁并不会被释放。

     */

}
