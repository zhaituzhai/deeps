package com.zhaojm.basal.concurrent.share;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SerialNumberGenerator{
    // 将一个域为 volatile， 那么他就会告诉编译器不要执行任何移除读取和操作的优化，这些操作的目的是
    // 用线程中的局部变量维护对这个域的精确同步
    // 实际上，读取和写入都是直接针对内存的，而却没用被缓存。
    // 但是 volatile 并不能对递增不是原子性操作这一事实产生影响
    private static volatile int serialNumber = 0;
    public static int nextSerialNumber(){
        return serialNumber++;
    }
}

class CircularSet{
    private int[] array;
    private int len;
    private int index = 0;

    public CircularSet(int size) {
        array = new int[size];
        len = size;
        for (int i = 0; i < size; i++) {
            array[i] = -1;
        }
    }

    public synchronized void add(int i){
        array[index] = i;
        index = ++index % len;
    }

    public synchronized boolean contains(int val){
        for (int i = 0; i < len; i++) {
            if(array[i] == val) {
                return true;
            }
        }
        return false;
    }
}

public class SerialNumberCheck {
    private  static  final int SIZE = 10;
    private static CircularSet serials = new CircularSet(1000);
    private  static ExecutorService exec = Executors.newCachedThreadPool();
    static class SerialChecker implements  Runnable{
        @Override
        public void run() {
            while (true) {
                int serial = SerialNumberGenerator.nextSerialNumber();
                if(serials.contains(serial)){
                    System.out.println("Duplicate: " + serial);
                    System.exit(0);
                }
                serials.add(serial);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new SerialChecker());
        }
        if(args.length > 0){
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
            System.out.println("No duplicates detected");
            System.out.println(0);
        }
    }

}

