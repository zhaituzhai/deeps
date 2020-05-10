package com.zhaojm.jvm.b_classload;

/**
 * <clinit>() 加载时会被加锁
 * @author zhaojm
 * @date 2020/5/10 10:14
 */
public class AfClassInitTest {

    public static void main(String[] args) {
        Runnable r = () ->{
            System.out.println(Thread.currentThread().getName() + "开始");
            DeadThread dead = new DeadThread();
            System.out.println(Thread.currentThread().getName() + "结束");
        };

        Thread t1 = new Thread(r, "线程1");
        Thread t2 = new Thread(r, "线程2");

        t1.start();
        t2.start();
    }


}

class DeadThread {
    static {
        if(true){
            System.out.println(Thread.currentThread().getName() + "初始化当前类");
            while (true) {

            }
        }
    }
}