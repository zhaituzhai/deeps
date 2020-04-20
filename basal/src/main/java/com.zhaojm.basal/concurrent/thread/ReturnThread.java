package com.zhaojm.basal.concurrent.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 *
 * @author zhaojm
 * @date 2020-04-20 14:50
 */
public class ReturnThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "hello thread";
    }

    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<>(new ReturnThread());
        new Thread(futureTask).start();
        try {
            String result = futureTask.get();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
