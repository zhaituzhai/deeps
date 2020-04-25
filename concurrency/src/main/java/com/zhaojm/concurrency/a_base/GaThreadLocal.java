package com.zhaojm.concurrency.a_base;

/**
 * 多钱程访问同一个共享变量时特别容易出现并发问题，特别是在多个线程需要对一个共享变量进行写入时。
 * 为了保证线程安全，一般使用者在访问共享变量时需要进行适当的同步,同步的措施一般是加锁，这就需要
 * 使用者对锁有一定的了解， 这显然加重了使用者的负担。那么有没有一种方式可以做到，当创建一个变量
 * 后， 每个线程对其进行访问的时候访问的是自己线程的变量呢。
 *
 *  ThreadLocal 线程本地变量
 *
 * ThreadLocal 是JDK 包提供的，它提供了线程本地变量，也就是如果你创建了一个ThreadLocal 变量，
 * 那么访问这个变量的每个线程都会有这个变量的一个本地副本。当多个线程操作这个变量时，实际操作的是
 * 自己本地内存里面的变量，从而避免了线程安全问题。
 * 创建一个ThreadLocal 变量后，每个线程都会复制一个变量到自己的本地内存。
 * @author zhaojm
 * @date 2020-04-21 11:27
 */
public class GaThreadLocal {

    static ThreadLocal<String> localVariable = new ThreadLocal<>();

    static void print(String str) {
        // 1.1 打印当前线程本地内存中 localVariable 变量的值
        System.out.println(str + " : " + localVariable.get());
        // 1.2 清除当前线程本地内存中的 localVariable 变量
        localVariable.remove();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            // 设置线程中本地变量的 localVariable 的值
            // 设置的是线程1的本地内存中的一个副本，而这个附表其他线程是访问不到的
            localVariable.set("thread1 local variable");
            // 由线程1调用的print方法只能打印线程1设置的值
            print("thread1");
            System.out.println("thread1 remove after " + localVariable.get());
        });

        Thread t2 = new Thread(() -> {
            // 设置线程One 中本地交itlocal Variable 的位
            localVariable.set("thread2 local variable");
            print("thread2");
            System.out.println("thread2 remove after " + localVariable.get());
        });
        t1.start();
        t2.start();
    }
    /*
    输出结果
    没有remove
    thread2 : thread2 local variable
    thread1 : thread1 local variable
    thread1 remove after thread1 local variable
    thread2 remove after thread2 local variable

    remove之后
    thread1 : thread1 local variable
    thread2 : thread2 local variable
    thread1 remove after null
    thread2 remove after null

    注意：threadLocal 的值不支持继承性
        父线程的设置的值，子线程无法取到
        如果需要子线程查看到父线程的变量 InheritableThreadLocal

     */

}
