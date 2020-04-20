package com.zhaojm.basal.concurrent.sync;

/**
 * synchronized 同步语句块的情况
 * @author zhaojm
 * @date 2020/4/19 17:05
 */
public class SynchronizedDemo {
    public void method() {
        synchronized (this) {
            System.out.println("synchronized code");
        }
    }
    /*
    查看编译后的文件可以发现  (javap -c -s -v -l SynchronizedDemo.class)

    synchronized 同步语句块

    synchronized 同步语句块的实现使用的是monitorenter 和 monitorexit 指令，其中 monitorenter 指令指向同步代码块
    的开始位置，monitorexit 指令则指明同步代码块的结束位置。
    当执行 monitorenter 指令时，线程试图获取锁也就是获取monitor（monitor对象存在与每个java对象的对象头中，
    synchronized 锁便是通过这种方式获取锁的，也就是java中任意对象可以作为锁的原因）的持有权。当计数器为0
    则可以成功获取，获取后将锁计数器设为1也就是+1.相应于在执行monitorexit指令后，将锁计数器设置为0.表明锁
    被释放。如果获取对象锁失败，那当前线程就要阻塞等待，直到锁被另外一个线程释放为止。

     */

    // Classfile /D:/Workspaces/IDEASpace/deeps/basal/src/main/java/com.zhaojm.basal/concurrent/sync/SynchronizedDemo.class
    //  Last modified 2020-4-19; size 565 bytes
    //  MD5 checksum cf7255df4fcacbbfaef753dcdabc3ff4
    //  Compiled from "SynchronizedDemo.java"
    //public class com.zhaojm.basal.concurrent.sync.SynchronizedDemo
    //  minor version: 0
    //  major version: 52
    //  flags: ACC_PUBLIC, ACC_SUPER
    //Constant pool:
    //   #1 = Methodref          #6.#18         // java/lang/Object."<init>":()V
    //   #2 = Fieldref           #19.#20        // java/lang/System.out:Ljava/io/PrintStream;
    //   #3 = String             #21            // synchronized code
    //   #4 = Methodref          #22.#23        // java/io/PrintStream.println:(Ljava/lang/String;)V
    //   #5 = Class              #24            // com/zhaojm/basal/concurrent/sync/SynchronizedDemo
    //   #6 = Class              #25            // java/lang/Object
    //   #7 = Utf8               <init>
    //   #8 = Utf8               ()V
    //   #9 = Utf8               Code
    //  #10 = Utf8               LineNumberTable
    //  #11 = Utf8               method
    //  #12 = Utf8               StackMapTable
    //  #13 = Class              #24            // com/zhaojm/basal/concurrent/sync/SynchronizedDemo
    //  #14 = Class              #25            // java/lang/Object
    //  #15 = Class              #26            // java/lang/Throwable
    //  #16 = Utf8               SourceFile
    //  #17 = Utf8               SynchronizedDemo.java
    //  #18 = NameAndType        #7:#8          // "<init>":()V
    //  #19 = Class              #27            // java/lang/System
    //  #20 = NameAndType        #28:#29        // out:Ljava/io/PrintStream;
    //  #21 = Utf8               synchronized code
    //  #22 = Class              #30            // java/io/PrintStream
    //  #23 = NameAndType        #31:#32        // println:(Ljava/lang/String;)V
    //  #24 = Utf8               com/zhaojm/basal/concurrent/sync/SynchronizedDemo
    //  #25 = Utf8               java/lang/Object
    //  #26 = Utf8               java/lang/Throwable
    //  #27 = Utf8               java/lang/System
    //  #28 = Utf8               out
    //  #29 = Utf8               Ljava/io/PrintStream;
    //  #30 = Utf8               java/io/PrintStream
    //  #31 = Utf8               println
    //  #32 = Utf8               (Ljava/lang/String;)V
    //{
    //  public com.zhaojm.basal.concurrent.sync.SynchronizedDemo();
    //    descriptor: ()V
    //    flags: ACC_PUBLIC
    //    Code:
    //      stack=1, locals=1, args_size=1
    //         0: aload_0
    //         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
    //         4: return
    //      LineNumberTable:
    //        line 7: 0
    //
    //  public void method();
    //    descriptor: ()V
    //    flags: ACC_PUBLIC
    //    Code:
    //      stack=2, locals=3, args_size=1
    //         0: aload_0
    //         1: dup
    //         2: astore_1
    //         3: monitorenter
    //         4: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
    //         7: ldc           #3                  // String synchronized code
    //         9: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
    //        12: aload_1
    //        13: monitorexit
    //        14: goto          22
    //        17: astore_2
    //        18: aload_1
    //        19: monitorexit
    //        20: aload_2
    //        21: athrow
    //        22: return
    //      Exception table:
    //         from    to  target type
    //             4    14    17   any
    //            17    20    17   any
    //      LineNumberTable:
    //        line 9: 0
    //        line 10: 4
    //        line 11: 12
    //        line 12: 22
    //      StackMapTable: number_of_entries = 2
    //        frame_type = 255 /* full_frame */
    //          offset_delta = 17
    //          locals = [ class com/zhaojm/basal/concurrent/sync/SynchronizedDemo, class java/lang/Object ]
    //          stack = [ class java/lang/Throwable ]
    //        frame_type = 250 /* chop */
    //          offset_delta = 4
    //}
    //SourceFile: "SynchronizedDemo.java"
}
