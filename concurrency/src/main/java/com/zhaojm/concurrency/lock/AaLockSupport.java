package com.zhaojm.concurrency.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * JDK 中的11.jar 包里面的LockSupport 是个工具类，它的主要作用是挂起和唤醒线程，
 * 该工具类是创建锁和其他同步类的基础。
 *
 * LockSupport 类与每个使用它的线程都会关联一个许可证，在默认情况下调用
 * LockSupport 类的方法的线程是不持有许可证的。LockSupport 是使用Unsafe 类实现的，
 *
 * @author zhaojm
 * @date 2020/4/23 23:05
 */
public class AaLockSupport {

    public static void main(String[] args) {
        System.out.println("begin park!");
        // 使当前线程获取到许可证
        LockSupport.unpark(Thread.currentThread());

        // 再次调用park方法
        LockSupport.park();

        System.out.println("end park!");

    }

    /*

    // LockSupport.park();
    输出
    begin park!
    ...
    最终只会输出begin park ！，然后当前
    线程被挂起，这是因为在默认情况下调用线程是不持有许可证的。

    在其他线程调用 unpark(Thread thread）方法并且将当前线程作为参数时，调用park 方
    法而被阻塞的线程会返回。另外，如果其他线程调用了阻塞线程的in terrupt（）方法，设置
    了中断标志或者线程被虚假唤醒，则阻塞线程也会返回。所以在调用park 方法时最好也
    使用循环条件判断方式。
    需要注意的是，因调用p ark（） 方法而被阻塞的线程被其他线程中断而返回时并不会抛
    出In terruptedException 异常。

    //  LockSupport.unpark(Thread.currentThread());
    输出
    begin park!
    end park!


     */

}
