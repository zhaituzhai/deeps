package com.zhaojm.concurrency.base_a;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author zhaojm
 * @date 2020/4/21 22:34
 */
public class AbUnsafe {
    // 获取 Unsafe 的实例
    static final Unsafe unsafe;

    // 记录变量 state 在类 AbUnsafe 中的偏移位
    static final long stateOffset;

    // 变量
    private volatile long state = 0;

    static {
        try {
            // 第一种实现
            // 获取state 变量在类TestUnSafe 中的偏移值
            // 使用unsafe . objectFieldOffset 获取TestUnSafe 类里面的state 变量， 在
            // AbUnsafe 对象里面的内存偏移量地址并将其保存到stateOffset 变量中。
            // stateOffset = unsafe.objectFieldOffset(AbUnsafe.class.getDeclaredField("state"));

            // 第二种：使用反射
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = ((Unsafe) field.get(null));
            stateOffset = unsafe.objectFieldOffset(AbUnsafe.class.getDeclaredField("state"));


        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        // 创建实例，并且设置state 1直为1(2.2 . 5)
        AbUnsafe test = new AbUnsafe();
        // 调用创建的unsafe 实例的compareAndSwapint 方法，设置test 对象的state 变量的值。
        // 具体意思是，如果test 对象中内存偏移量为stateOffset 的state 变量的值为 0，则更新该值为1
        boolean success = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        System.out.println(success);
    }
    /*

    java.lang.ExceptionInInitializerError
    Caused by: java.lang.SecurityException: Unsafe
        at sun.misc.Unsafe.getUnsafe(Unsafe.java:90)
        at com.zhaojm.concurrency.base_a.AbUnsafe.<clinit>(AbUnsafe.java:10)
    Exception in thread "main"

    Unsafe.getUnsafe();
    判断是不是Bootstrap 类加载器加载的 localClass，在这里是看是
    不是 Bootstrap 加载器加载了 AbUnsafe.class  很明显由于TestUnSafe.class 是使用
    AppClassLoader 加载的， 所以这里直接抛出了异常。
    我们知道Unsafe 类是吐jar 包提供的， rt.jar 包
    里面的类是使用Bootstrap 类加载器加载的，而我们的启动main 函数所在的类是使用
    AppC! assLoader 加载的，所以在main 函数里面加载Unsafe 类时伞，根据委托机制，
    会委托 给Bootstrap 去加载Unsafe 类。
     */

}
