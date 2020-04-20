package com.zhaojm.concurrency.base;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 有返回值的线程
 * @author zhaojm
 * @date 2020-04-20 14:50
 */
public class AcReturnThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "hello thread";
    }

    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<>(new AcReturnThread());
        new Thread(futureTask).start();
        try {
            String result = futureTask.get();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
