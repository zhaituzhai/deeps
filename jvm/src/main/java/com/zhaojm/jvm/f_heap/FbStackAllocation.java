package com.zhaojm.jvm.f_heap;

/**
 * // 栈上分配
 * -Xmx1G -Xms1G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails
 *
 *  开启逃逸分配与关闭逃逸分析对比
 *
 * @author zhaojm
 * @date 2020/5/17 17:41
 */
public class FbStackAllocation {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {
            alloc();
        }

        long end = System.currentTimeMillis();
        System.out.println("花费时间： " + (end - start) + " ms");

        // 方便观察堆中对象个数
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void alloc() {
        User user = new User();  // 未发生逃逸
    }

    static class User{

    }
}
