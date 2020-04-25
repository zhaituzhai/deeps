package com.zhaojm.concurrency.b_base;

/**
 * @author zhaojm
 * @date 2020/4/21 22:58
 */
public class BaRearrange extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if(read){ // 1
                System.out.println(num + num); // 2
            }
            System.out.println("read thread....");
        }
    }

    public static class WriteThread extends Thread{
        @Override
        public void run() {
            num = 2; // 3
            read = true; // 4
            System.out.println("writeThread set over...");
        }
    }

    private static int num = 0;
    private static boolean read = false;

    public static void main(String[] args) throws InterruptedException {
        BaRearrange rt = new BaRearrange();
        rt.start();

        WriteThread wt = new WriteThread();
        wt.start();

        Thread.sleep(10);
        rt.interrupt();
        System.out.println("main exit.");
    }

    /*

    可能会输 0
    


     */

}
