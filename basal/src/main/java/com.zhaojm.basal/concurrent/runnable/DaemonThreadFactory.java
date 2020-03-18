package com.zhaojm.basal.concurrent.runnable;

import java.util.concurrent.ThreadFactory;

/**
 * 编写定制的ThreadFactory设置创建线程的属性
 */
public class DaemonThreadFactory implements ThreadFactory {

    // 定制一个可以设置线程优先级的工厂
    private final int priority;

    public DaemonThreadFactory(int priority) {
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.setPriority(priority);
        return t;
    }
}
