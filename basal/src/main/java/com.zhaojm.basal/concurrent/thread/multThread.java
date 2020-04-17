package com.zhaojm.basal.concurrent.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author zhaojm
 * @date 2020/4/17 22:03
 */
public class multThread {
    public static void main(String[] args) {
        // 获取 java 线程管理 MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印 ID 和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() +"]" + threadInfo.getThreadName());
        }

        // [6]Monitor Ctrl-Break
        //[5]Attach Listener
        //[4]Signal Dispatcher
        //[3]Finalizer
        //[2]Reference Handler
        //[1]main
    }
}
