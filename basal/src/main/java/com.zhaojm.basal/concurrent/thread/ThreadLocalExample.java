package com.zhaojm.basal.concurrent.thread;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * @author zhaojm
 * @date 2020/4/19 18:35
 */
public class ThreadLocalExample implements Runnable {

    private static final ThreadLocal<SimpleDateFormat> formatter =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyMMdd HHmm"));

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalExample obj = new ThreadLocalExample();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(obj, "" + i);
            Thread.sleep(new Random().nextInt(1000));
            t.start();
        }
    }

    @Override
    public void run() {
        System.out.println("Thread Name = " + Thread.currentThread().getName() + " default Formatter = " + formatter.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        formatter.set(new SimpleDateFormat());
        System.out.println("Thread Name = " + Thread.currentThread().getName() + " default Formatter = " + formatter.get().toPattern());
    }


    /*
    formatter.set(new SimpleDateFormat());

    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }

    最终的变量是放在当前线程的ThreadLocalMap 中 并不是存在ThreadLocal 上 ThreadLoacal 可以理解为只是ThradLocalMap 的封装
    传递了变量值。ThreadLocal类中可以通过Thread.currentThread（）获取到当前线程对象后，可以战场通过 getMap(thread t) 可以访问到
    该线程的ThreadLocalMap 对象

    ThreadLocal 內部维护的是一个类似Map的ThreadLocalMap数据结构，key为当前对象的Thread对象，值为Object对象。

     */

    /*

    ThreadLocal values pertaining to this thread. This map is maintained by the ThreadLocal class.
    与此线程有关的ThreadLocal值。该Map由ThreadLocal类维护。
    ThreadLocal.ThreadLocalMap threadLocals = null;


    InheritableThreadLocal values pertaining to this thread. This map is
    maintained by the InheritableThreadLocal class.

    与此线程有关的InheritableThreadLocal值。该Map由InheritableThreadLocal类维护。
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;


     */

}
