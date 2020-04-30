package com.zhaojm.concurrency.d_lock;

/**
 * 两重检查的单例模式
 * @author zhaojm
 * @date 2020-04-30 9:13
 */
public class FaDoubleCheckSingleton {

    /**
     * 使用 volatile 修饰，可见性与禁用指令重排
     */
    private static volatile FaDoubleCheckSingleton singleton;

    /**
     * 单例模式：私有化构造器不允许其他类实例使用。
     */
    private FaDoubleCheckSingleton() {
    }

    /**
     * 获取单例实例
     * @return
     */
    public static FaDoubleCheckSingleton getInstance() {
        if (null == singleton) {
            // 对象锁
            synchronized (FaDoubleCheckSingleton.class) {
                if(null == singleton) {
                    singleton = new FaDoubleCheckSingleton();
                }
            }
        }
        return singleton;
    }
}
