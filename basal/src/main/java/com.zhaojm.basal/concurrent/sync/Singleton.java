package com.zhaojm.basal.concurrent.sync;

/**
 * 双重校验锁实现对象单例（线程安全）
 * @author zhaojm
 * @date 2020/4/19 16:56
 */
public class Singleton {
    // 使用 volatile 可以禁止 JVM 的指令重排，保证在多线程环境下也能正常执行
    private volatile static Singleton uniqueInstance;

    public Singleton() {
    }

    private static Singleton getUniqueInstance() {
        // 先判断对象是否已经实例过，没有实例化过才进入加锁代码
        if(null == uniqueInstance) {
            // 类对象加锁
            synchronized (Singleton.class) {
                if(null == uniqueInstance) {
                    // 分三步
                    // 1、 为uniqueInstance f分配内存空间
                    // 2、初始化uniqueInstance
                    // 3、将 uniqueInstance 指向分配的内存地址
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
