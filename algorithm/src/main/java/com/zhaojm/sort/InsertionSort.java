package com.zhaojm.sort;

/**
 * 插入排序
 * 设定一个一个来，第一个位置为已排序的位置，没增加一个数就和已排序的比较，找到顺序位置
 */
public class InsertionSort implements ISort{
    @Override
    public int[] sort(int[] arr) {
        int length = arr.length;
        int temp;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if(arr[j] < arr[j - 1]) {
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }
}
