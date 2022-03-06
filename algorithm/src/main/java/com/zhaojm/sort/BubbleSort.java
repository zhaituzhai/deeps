package com.zhaojm.sort;

/**
 * 冒泡排序
 * 重复遍历要排序的数列，两两比较，小的放前面，大大放后面
 * 1. 比较
 */
public class BubbleSort implements ISort {

    @Override
    public int[] sort(int[] arr){
        int length = arr.length;
        int temp;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i -1; j++) {
                if(arr[j] > arr[j + 1]) {
                    temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

}
