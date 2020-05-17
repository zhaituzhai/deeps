package com.zhaojm.jvm.f_heap;

import java.util.ArrayList;
import java.util.List;

/**
 * test minor gc ,major gc, full gc
 *
 * VM 参数 -Xms9M -Xmx9M -XX:+PrintGCDetails
 *
 * @author zhaojm
 * @date 2020/5/17 15:05
 */
public class DbGCTest {
    public static void main(String[] args) {
        int i = 0;
        try {
            List<String> list = new ArrayList<>();
            String a = "testString";
            while (true) {
                list.add(a);
                a = a+a;
                i++;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("遍历次数 "+ i);
        }
    }

    /*

    [GC (Allocation Failure) [PSYoungGen: 2040K->500K(2560K)] 2040K->826K(9728K), 0.0018048 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
    [GC (Allocation Failure) [PSYoungGen: 2137K->504K(2560K)] 2463K->1970K(9728K), 0.0010878 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
    [GC (Allocation Failure) [PSYoungGen: 2462K->216K(2560K)] 7768K->6162K(9728K), 0.0009209 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
    [GC (Allocation Failure) [PSYoungGen: 216K->272K(2560K)] 6162K->6218K(9728K), 0.0004336 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
    [Full GC (Allocation Failure) [PSYoungGen: 272K->0K(2560K)] [ParOldGen: 5946K->4493K(7168K)] 6218K->4493K(9728K), [Metaspace: 3226K->3226K(1056768K)], 0.0034739 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
    [GC (Allocation Failure) [PSYoungGen: 80K->32K(2560K)] 7134K->7085K(9728K), 0.0004123 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
    [Full GC (Ergonomics) [PSYoungGen: 32K->0K(2560K)] [ParOldGen: 7053K->5773K(7168K)] 7085K->5773K(9728K), [Metaspace: 3226K->3226K(1056768K)], 0.0027976 secs] [Times: user=0.02 sys=0.00, real=0.00 secs]
    [GC (Allocation Failure) [PSYoungGen: 0K->0K(1536K)] 5773K->5773K(8704K), 0.0003650 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
    [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(1536K)] [ParOldGen: 5773K->5726K(7168K)] 5773K->5726K(8704K), [Metaspace: 3226K->3226K(1056768K)], 0.0088431 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
    // oom 之前执行一次 full GC
    Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at java.util.Arrays.copyOfRange(Arrays.java:3664)
        at java.lang.String.<init>(String.java:207)
        at java.lang.StringBuilder.toString(StringBuilder.java:407)
        at com.zhaojm.jvm.f_heap.DbGCTest.main(DbGCTest.java:19)
    Heap
     PSYoungGen      total 1536K, used 49K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
      eden space 1024K, 4% used [0x00000000ffd00000,0x00000000ffd0c798,0x00000000ffe00000)
      from space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
      to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
     ParOldGen       total 7168K, used 5726K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
      object space 7168K, 79% used [0x00000000ff600000,0x00000000ffb97838,0x00000000ffd00000)
     Metaspace       used 3258K, capacity 4496K, committed 4864K, reserved 1056768K
      class space    used 353K, capacity 388K, committed 512K, reserved 1048576K

     */

}
