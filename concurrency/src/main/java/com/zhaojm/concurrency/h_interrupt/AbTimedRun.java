package com.zhaojm.concurrency.h_interrupt;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AbTimedRun {

    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor(5);

    /**
     * 在外部线程中安排中断（建议不要这么做）
     * 破坏了规则：在中断之前了解线程的中断策略。
     * 由于此方法可以从任意一个线程中调用，因此无法知道调用这个线程的中断策略。
     * 如果任务在超时之前完成，需要取消这个限时任务，并将返回到调用方法中去
     * @param r
     * @param timeout
     * @param unit
     */
    public void interruptedOutside(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        final Thread t = Thread.currentThread();
        cancelExec.schedule(() -> t.interrupt(), timeout, unit);
        r.run();
    }


    public void interruptedInside(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() {
                if (null != t) {
                    launderThrowable(t);
                }
            }
        }
        RethrowableTask rethrowableTask = new RethrowableTask();
        Thread thread = new Thread(rethrowableTask);
        thread.start();
        cancelExec.schedule(() -> thread.interrupt(), timeout, unit);
        thread.join(unit.toMillis(timeout));
        rethrowableTask.rethrow();

    }

    public static void  launderThrowable(Throwable t){
        if(t instanceof RuntimeException)
            throw  (RuntimeException)t;
        else if(t instanceof Error)
            throw (Error)t;
        else throw new RuntimeException(t);
    }

    private static final ExecutorService taskExecutor = Executors.newFixedThreadPool(5);
    public void interruptedFuture(Runnable r, long timeout, TimeUnit unit) {
        Future<?> task = cancelExec.submit(r);

        try {
            task.get();
        } catch (InterruptedException e) {
            // 接下来任务将被取消
            e.printStackTrace();
        } catch (ExecutionException e) {
            // 如果在任务中抛出异常，那么重新抛出异常
            launderThrowable(e.getCause());
        } finally {
            // 如果任务已经结束，那么执行取消操作也不会带来任何影响，如果任务正在执行，则会被取消
            task.cancel(true);
        }

    }

}
