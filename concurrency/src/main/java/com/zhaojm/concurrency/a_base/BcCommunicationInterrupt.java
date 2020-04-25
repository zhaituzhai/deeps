package com.zhaojm.concurrency.a_base;

/**
 * 当一个线程调用共享对象的 wait() 方法被阻塞挂起后，
 * 如果其他线程中断了该线程， 则该线程会抛出InterruptedException 异常并返回。
 * @author zhaojm
 * @date 2020-04-20 17:47
 */
public class BcCommunicationInterrupt {
    private static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("---- start ----");
                synchronized (obj) {
                    obj.wait();
                }
                System.out.println("----- end -----");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t1.start();
        Thread.sleep(1000);

        System.out.println("----- start interrupt thread -----");
        t1.interrupt();
        System.out.println("----- end interrupt -----");
    }
}
