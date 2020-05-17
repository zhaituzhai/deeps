package com.zhaojm.jvm.f_heap;

/**
 * 1. 设置堆空间 (新生代+老年代)
 * -Xms 用来设置堆空间的初始内存大小
 *      -X 是jvm的运行参数
 *      ms 是 memory start
 * -Xmx 设置堆空间内存最大值
 * 2. 默认堆空间大小
 *      初始值内存大小：物理电脑内存 /64
 *      最大值内存大小：物理电脑内存 /4
 * 3. 手动设置 -Xms600 -Xmx600
 *      设置两者相同值，避免垃圾回收之后内存重新计算
 * 4. 查看设置的参数
 *      方式1：jps  / jstat -gc 进程id
 *      方式2：-XX:+PrintGCDetails
 *  Heap
 *  PSYoungGen      total 179200K, used 12288K [0x00000000f3800000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 153600K, 8% used [0x00000000f3800000,0x00000000f44001b8,0x00000000fce00000)
 *   from space 25600K, 0% used [0x00000000fe700000,0x00000000fe700000,0x0000000100000000)
 *   to   space 25600K, 0% used [0x00000000fce00000,0x00000000fce00000,0x00000000fe700000)
 *  ParOldGen       total 409600K, used 0K [0x00000000da800000, 0x00000000f3800000, 0x00000000f3800000)
 *   object space 409600K, 0% used [0x00000000da800000,0x00000000da800000,0x00000000f3800000)
 *  Metaspace       used 3231K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 350K, capacity 388K, committed 512K, reserved 1048576K
 * @author zhaojm
 * @date 2020/5/17 11:15
 */
public class BaHeapSpaceInitial {
    public static void main(String[] args) {
        // Java 虚拟机中堆内存总量
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        // 试图使用最大堆内存量
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        System.out.println("-Xms :" + initialMemory + "M");
        System.out.println("-Xmx :" + maxMemory + "M");

//        System.out.println("系统内存大小为： " + initialMemory * 64.0 / 1024 + "G");
//        System.out.println("系统内存大小为： " + maxMemory * 4.0 / 1024 + "G");

//        try {
//            Thread.sleep(1000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
