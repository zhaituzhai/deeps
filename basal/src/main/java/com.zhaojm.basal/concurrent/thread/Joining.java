package com.zhaojm.basal.concurrent.thread;

class Sleeper extends Thread {
    private int duration;

    public Sleeper(String name, int duration) {
        super(name);
        this.duration = duration;
        start();
    }

    @Override
    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted. " + "isInterrupted(): " + isInterrupted());
        }
        System.out.println(getName() + " has awakened");
    }
}

class Joiner extends Thread {
    private Sleeper sleeper;

    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println(getName() + " join completed");
    }
}

/**
 * 1、一个线程可以在其他线程之上调用join()方法，其效果是等待一段时间直到第二个线程结束才继续执行
 * 如果某个线程在另一个线程t上调用t.join()，此线程将被挂起，直到目标线程t结束才恢复（t.isAlive() 返回假）
 * 2、也可以在调用join()带上一个超时参数，这样如果目标线程在这段时间到期还没有结束的化，join()方法总能返回
 * 3、join()方法的调用可以被打断，做法是在调用线程上调用interrupt()方法。
 */
public class Joining {
    public static void main(String[] args) {
        Sleeper sleeper = new Sleeper("Sleepy", 1500), grumpy = new Sleeper("Grumpy", 1500);
        Joiner dopey = new Joiner("Dopey", sleeper), doc = new Joiner("Doc", grumpy);
        grumpy.interrupt();

        // sleeper、grumpy 开始运行
        // dopey、doc 开始运行，线程中调用了  x.join();当前线程挂起
        // grumpy 被打断
        // doc 中的grumpy线程执行完成，doc继续执行
        // sleeper 线程运行完成  dopey 继续运行

        // Grumpy was interrupted. isInterrupted(): false
        // Grumpy has awakened
        // Doc join completed
        // Sleepy has awakened
        // Dopey join completed
    }
}
