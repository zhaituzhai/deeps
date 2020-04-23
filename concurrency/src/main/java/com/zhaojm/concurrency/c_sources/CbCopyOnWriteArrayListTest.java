package com.zhaojm.concurrency.c_sources;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zhaojm
 * @date 2020/4/23 23:00
 */
public class CbCopyOnWriteArrayListTest {
    private static volatile CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        arrayList.add("hello");
        arrayList.add("alibaba");
        arrayList.add("welcome");
        arrayList.add("to");
        arrayList.add("hangzhou");

        Thread threadOne = new Thread(() -> {
            arrayList.set(1, "baba");

            arrayList.remove(2);
            arrayList.remove(3);
        });

        Iterator<String> iterator = arrayList.iterator();

        threadOne.start();

        threadOne.join();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
    /*
    输出结果
    hello
    alibaba
    welcome
    to
    hangzhou

    首先初始化了arrayList ， 然后在启动线程前获取到了
    array List 迭代器。子线程thread One 启动后首先修改了arrayList 的第一个元素的值， 然后
    删除了aηayList 中下标为2 和3 的元素。
    主线程在子线程执行完毕后使用获取的迭代器遍历数组元素，从输出结果我们知道，
    在子线程里面进行的操作一个都没有生效，这就是选代器弱一致性的体现。需要注意的是，
    获取迭代器的操作必须在子线程操作之前进行。


     */

}
