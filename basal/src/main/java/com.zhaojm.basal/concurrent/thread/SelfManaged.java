package com.zhaojm.basal.concurrent.thread;

/**
 * 自管理的Runnable
 * 如果extend Thread 则不可以继承其他类
 */
public class SelfManaged implements Runnable {
    private int countDown = 5;
    private Thread t = new Thread(this);

    public SelfManaged() {
        // 在构造器中启动线程可能有问题，因为另一个任务可能会在构造器结束之前开始执行，
        // 这意味着该任务能够访问出入不稳定的对象。 这是优选Executor而不是显示创建Thread对象的另一个原因
        t.start();
    }

    @Override
    public String toString() {
        return Thread.currentThread().getName() + "(" + countDown + "), ";
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(this);
            if(--countDown == 0)
                return;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new SelfManaged();
        }
    }
}
