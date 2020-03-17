package com.zhaojm.basal.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Callable 是一种具有类型参数的泛型，它的类型参数表示是从方法call()中返回的值  并且必须时使用ExecutorService.submit调用
 * callable,在任务完成时返回一个值
 */
class TaskWithResult implements Callable<String> {

    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        return "result of TaskWithResult " + id;
    }
}

public class CallableDemo{
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<String>> results = new ArrayList<>();
//        results.add(exec.submit(() -> "result of TaskWithResult " + id));
        for (int i = 0; i < 10; i++) {
            results.add(exec.submit(new TaskWithResult(i)));
        }
        for (Future<String> result : results) {
            try {

                if(result.isDone()) {
                    System.out.println(result.get());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                exec.shutdown();
            }
        }
    }
}
