package com.zhaojm.jvm.i_string;

/**
 * @author zhaojm
 * @date 2020/5/25 21:44
 */
public class BdStringInternTest {
    static final int MAX_COUNT = 1000 * 10000;
    static final String[] arr = new String[MAX_COUNT];
    public static void main(String[] args) {
        Integer[] data = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_COUNT; i++) {
            arr[i] = new String(String.valueOf(data[i % data.length]));  // 129 7678
//            arr[i] = new String(String.valueOf(data[i % data.length])).intern(); // 462 // 4466
        }
        System.out.println("花费的时间:" + (System.currentTimeMillis() - start));
        try{
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
