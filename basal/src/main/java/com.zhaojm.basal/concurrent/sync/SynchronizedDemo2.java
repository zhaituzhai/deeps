package com.zhaojm.basal.concurrent.sync;

/**
 * @author zhaojm
 * @date 2020/4/19 17:29
 */
public class SynchronizedDemo2 {
    public synchronized void method() {
        System.out.println("synchronized method");
    }


    /*

    synchronized 修饰方法的情况

    synchronizde 修饰方法的并没有monitorennter 指令和monitorexit指令，取得代之的确是 ACC_SYNCHRONIZED 标志
    该标志指明了给方法是一个同步方法， JVM 通过该 ACC_SYNCHRONIZED 访问标志来辨别一个方法是否声明为同步方法，从
    而执行相应的同步调用


     */

    // 加了synchronized

    // D:\Workspaces\IDEASpace\deeps\basal\src\main\java\com.zhaojm.basal\concurrent\sync>javap -c -s -v -l SynchronizedDemo2.class
    //Classfile /D:/Workspaces/IDEASpace/deeps/basal/src/main/java/com.zhaojm.basal/concurrent/sync/SynchronizedDemo2.class
    //  Last modified 2020-4-19; size 457 bytes
    //  MD5 checksum 3124668eaae4328975334729b0321256
    //  Compiled from "SynchronizedDemo2.java"
    //public class com.zhaojm.basal.concurrent.sync.SynchronizedDemo2
    //  minor version: 0
    //  major version: 52
    //  flags: ACC_PUBLIC, ACC_SUPER
    //Constant pool:
    //   #1 = Methodref          #6.#14         // java/lang/Object."<init>":()V
    //   #2 = Fieldref           #15.#16        // java/lang/System.out:Ljava/io/PrintStream;
    //   #3 = String             #17            // synchronized method
    //   #4 = Methodref          #18.#19        // java/io/PrintStream.println:(Ljava/lang/String;)V
    //   #5 = Class              #20            // com/zhaojm/basal/concurrent/sync/SynchronizedDemo2
    //   #6 = Class              #21            // java/lang/Object
    //   #7 = Utf8               <init>
    //   #8 = Utf8               ()V
    //   #9 = Utf8               Code
    //  #10 = Utf8               LineNumberTable
    //  #11 = Utf8               method
    //  #12 = Utf8               SourceFile
    //  #13 = Utf8               SynchronizedDemo2.java
    //  #14 = NameAndType        #7:#8          // "<init>":()V
    //  #15 = Class              #22            // java/lang/System
    //  #16 = NameAndType        #23:#24        // out:Ljava/io/PrintStream;
    //  #17 = Utf8               synchronized method
    //  #18 = Class              #25            // java/io/PrintStream
    //  #19 = NameAndType        #26:#27        // println:(Ljava/lang/String;)V
    //  #20 = Utf8               com/zhaojm/basal/concurrent/sync/SynchronizedDemo2
    //  #21 = Utf8               java/lang/Object
    //  #22 = Utf8               java/lang/System
    //  #23 = Utf8               out
    //  #24 = Utf8               Ljava/io/PrintStream;
    //  #25 = Utf8               java/io/PrintStream
    //  #26 = Utf8               println
    //  #27 = Utf8               (Ljava/lang/String;)V
    //{
    //  public com.zhaojm.basal.concurrent.sync.SynchronizedDemo2();
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
    //  public synchronized void method();
    //    descriptor: ()V
    //    flags: ACC_PUBLIC, ACC_SYNCHRONIZED
    //    Code:
    //      stack=2, locals=1, args_size=1
    //         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
    //         3: ldc           #3                  // String synchronized method
    //         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
    //         8: return
    //      LineNumberTable:
    //        line 9: 0
    //        line 10: 8
    //}
    //SourceFile: "SynchronizedDemo2.java"


    // 没有加 synchronized

    // Classfile /D:/Workspaces/IDEASpace/deeps/basal/src/main/java/com.zhaojm.basal/concurrent/sync/SynchronizedDemo2.class
    //  Last modified 2020-4-19; size 457 bytes
    //  MD5 checksum 0b9afcf94bcd11d20a9d5c904d213221
    //  Compiled from "SynchronizedDemo2.java"
    //public class com.zhaojm.basal.concurrent.sync.SynchronizedDemo2
    //  minor version: 0
    //  major version: 52
    //  flags: ACC_PUBLIC, ACC_SUPER
    //Constant pool:
    //   #1 = Methodref          #6.#14         // java/lang/Object."<init>":()V
    //   #2 = Fieldref           #15.#16        // java/lang/System.out:Ljava/io/PrintStream;
    //   #3 = String             #17            // synchronized method
    //   #4 = Methodref          #18.#19        // java/io/PrintStream.println:(Ljava/lang/String;)V
    //   #5 = Class              #20            // com/zhaojm/basal/concurrent/sync/SynchronizedDemo2
    //   #6 = Class              #21            // java/lang/Object
    //   #7 = Utf8               <init>
    //   #8 = Utf8               ()V
    //   #9 = Utf8               Code
    //  #10 = Utf8               LineNumberTable
    //  #11 = Utf8               method
    //  #12 = Utf8               SourceFile
    //  #13 = Utf8               SynchronizedDemo2.java
    //  #14 = NameAndType        #7:#8          // "<init>":()V
    //  #15 = Class              #22            // java/lang/System
    //  #16 = NameAndType        #23:#24        // out:Ljava/io/PrintStream;
    //  #17 = Utf8               synchronized method
    //  #18 = Class              #25            // java/io/PrintStream
    //  #19 = NameAndType        #26:#27        // println:(Ljava/lang/String;)V
    //  #20 = Utf8               com/zhaojm/basal/concurrent/sync/SynchronizedDemo2
    //  #21 = Utf8               java/lang/Object
    //  #22 = Utf8               java/lang/System
    //  #23 = Utf8               out
    //  #24 = Utf8               Ljava/io/PrintStream;
    //  #25 = Utf8               java/io/PrintStream
    //  #26 = Utf8               println
    //  #27 = Utf8               (Ljava/lang/String;)V
    //{
    //  public com.zhaojm.basal.concurrent.sync.SynchronizedDemo2();
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
    //      stack=2, locals=1, args_size=1
    //         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
    //         3: ldc           #3                  // String synchronized method
    //         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
    //         8: return
    //      LineNumberTable:
    //        line 9: 0
    //        line 10: 8
    //}
    //SourceFile: "SynchronizedDemo2.java"

}
