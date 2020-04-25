package com.zhaojm.concurrency.a_base;

/**
 * 简单下线程创建
 * @author zhaojm
 * @date 2020-04-20 15:24
 */
public class AaSimpleThread extends Thread {
    private int countDown = 5;
    private static int threadCount = 0;

    public AaSimpleThread(){
        super(Integer.toString(++threadCount));
        start();
    }

    @Override
    public String toString() {
        return "#" + getName() + "(" + countDown + "), ";
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(this);
            if(--countDown == 0){
                return;
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new AaSimpleThread();
        }
    }

    /*

    #1(5), #2(5), #1(4), #2(4), #1(3), #2(3), #1(2), #2(2), #1(1), #2(1), #3(5),
    #3(4), #3(3), #3(2), #3(1), #4(5), #4(4), #4(3), #4(2), #4(1), #5(5), #5(4),
    #5(3), #5(2), #5(1),

     */

}
