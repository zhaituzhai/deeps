package com.zhaojm.basal.concurrent.share;

class DualSynch {
    private Object syncObject = new Object();
    public synchronized void f() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "  =====  f()");
            Thread.yield();
        }
    }

    public void g() {
        // 同步syncObject 与 f() 互相独立  如果是同步this  则 f() 无法调用了
        // main  =====  f()
        // main  =====  f()
        // main  =====  f()
        // main  =====  f()
        // main  =====  f()
        // Thread-0  ======  g()
        // Thread-0  ======  g()
        // Thread-0  ======  g()
        // Thread-0  ======  g()
        // Thread-0  ======  g()
        synchronized(this) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "  ======  g()");
                Thread.yield();
            }
        }
    }
}

public class SyncObject {
    public static void main(String[] args) {
        final DualSynch ds = new DualSynch();
        /**
         * DualSync.f() 通过整个方法  在this同步，而g() 有一个在syncObject上同步的synchronized块
         * 因此这两个同步是相互独立的
         */
        new Thread() {
            @Override
            public void run() {
                ds.f();
            }
        }.start();

        ds.g();
        // main======  g()
        // main======  g()
        // Thread-0=====  f()
        // main======  g()
        // Thread-0=====  f()
        // main======  g()
        // Thread-0=====  f()
        // main======  g()
        // Thread-0=====  f()
        // Thread-0=====  f()
    }
}
