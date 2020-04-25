package com.zhaojm.concurrency.a_base;

/**
 * threadLocal 的值不支持继承性  父线程的设置的值，子线程无法取到
 * InheritableThreadLocal
 * 在 Thread init时
 *  if (parent.inheritableThreadLocals != null)
 *     this.inheritableThreadLocals =
 *         ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
 * @author zhaojm
 * @date 2020-04-21 16:58
 */
public class GbInheritableThreadLocal {

    private static final ThreadLocal<String> THREAD_LOCAL = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        THREAD_LOCAL.set("main thread local");
        Thread t1 = new Thread(() -> {
            System.out.println("thread1 " + THREAD_LOCAL.get());
        });
        t1.start();
        System.out.println("main thread + " + THREAD_LOCAL.get());
    }

    /*
    输出：
    使用 ThreadLocal ：
            main thread +main thread local
            thread1 null

    使用 InheritableThreadLocal
            main thread +main thread local
            thread1 main thread local


     */

}
