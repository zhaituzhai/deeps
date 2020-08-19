package com.zhaojm.concurrency.h_interrupt;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author zhaojm
 */
public class AaCancelThread extends Thread {

    private final BlockingQueue<BigInteger> queue;
    public AaCancelThread(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        boolean interrupted = false;
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p = p.nextProbablePrime()); // 如果队形已经满载，再次put就会阻塞
                System.out.println(p);
            }
        } catch (InterruptedException e) {
            /* 允许线程退出 */
            // 如果中断了一个阻塞状态的线程，会清除中断状态，
            interrupted = true;
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt(); // 恢复中断状态
            }
        }
    }

    public void cancel() {
        interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<>(20000);
        AaCancelThread aaCancelThread = new AaCancelThread(queue);
        aaCancelThread.start();
        Thread.sleep(8);
        aaCancelThread.cancel();
        System.out.println("aaCancelThread.isInterrupted() = " + aaCancelThread.isInterrupted());
        // aaCancelThread.isInterrupted() = false
        // 中断一个阻塞的线程，1.清楚中断状态，2.抛出中断异常

        // 中断一个未阻塞的线程，返回  aaCancelThread.isInterrupted() = true

        System.out.println("queue.size() = " + queue.size());
    }
}
