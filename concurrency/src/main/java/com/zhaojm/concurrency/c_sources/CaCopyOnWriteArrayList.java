package com.zhaojm.concurrency.c_sources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * CopyOnWriteArrayList 是一个线程安全的ArrayList ，
 * 对其进行的修改操作都是在底层的一个复制的数组（快照）上进行的，
 * 也就是使用了写时复制策略。
 *
 * 每个CopyOnWriteArray List 对象里面有一个array 数组对象用来存放具体元素，
 * ReentrantLock 独占锁对象用来保证同时只有－个线程对 array 进行修改。
 * 这里只要记得ReentrantLock 是独占锁，同时只有一个线程可以获取就可以了.
 *
 * @author zhaojm
 * @date 2020/4/23 22:23
 */
public class CaCopyOnWriteArrayList {

    public static void main(String[] args) {

        // 初始化 无参 setArray(new Object[0]);
        List<String> writeArrayList = new CopyOnWriteArrayList<>();

        // 有参数 Arrays.copyOf(elements, elements.length, Object[].class);
        // 将集合里面的元素复制到本list中
        List<String> strList = new ArrayList<>();
        strList.add("hello");
        List<String> list1 = new CopyOnWriteArrayList<>(strList);

        // 添加元素
        // 1、获取独占锁
        // 2、获取array
        // 3、复制array到新数组，添加元素到新数组
        // 4、使用新数组替换添加前的数组
        // 5、释放锁
        writeArrayList.add("world");
        writeArrayList.add("你好");

        // 获取元素
        // a、获取 array 数组
        // b、通过下标访问指定位置的元素
        // 如果期间输出元素
        // 2、进行复制操作，复制当前array数组，然后在水池的数组里面删除线程
        // 此时 get 方法获取的时删除之前的数组
        /*
        所以，虽然线程y 己经删除了index 处的元素，但是线程x 的步骤B 还是会返回
        index 处的元素，这其实就是写时复制策略产生的弱一致性问题。
         */
        System.out.println(writeArrayList.get(1));

        // 删除元素
        // 1、获取锁
        // 2、获取数组
        // 3、获取指定元素
        // 4.1、如果删除是最后一个元素
        // 4.2、不是，分两次复制删除后剩余的元素到新数组中
        // 5、使用新数组代替老数组
        // 6、释放锁
        writeArrayList.remove(1);

        // set 值
        // 首先获取了独占锁，从而阻止其他线程对array 数组进行修改，然后获取当
        // 前数组，并调用get 方法获取指定位置的元素，如果指定位置的元素值与新值不一致则创
        // 建新数组井复制元素，然后在新数组上修改指定位置的元素值并设置新数组到array。如
        // 果指定位置的元素值与新值一样，则为了保证volatile 语义，还是需要重新设置array，虽
        // 然array 的内容并没有改变。
        writeArrayList.set(1, "welcome");

        // 弱一致性的迭代器
        Iterator<String> iterator = writeArrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }

    /*

    CopyOn WriteArrayList 使用写时复制的策略来保证list 的一致性，而获取一修改一写
    入三步操作并不是原子性的，所以在增删改的过程中都使用了独占锁，来保证在某个时间
    只有一个线程能对list 数组进行修改。另外CopyOn WriteAn·ayList 提供了弱一致性的法代
    器， 从而保证在获取迭代器后，其他线程对list 的修改是不可见的， 迭代器遍历的数组是
    一个快照。


     */


}
