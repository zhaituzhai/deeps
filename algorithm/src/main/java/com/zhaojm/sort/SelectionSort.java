package com.zhaojm.sort;

/**
 * 选择排序
 */
public class SelectionSort implements ISort {

    @Override
    public int[] sort(int[] arr) {
        int length = arr.length;
        int temp;
        for (int i = 0; i < length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < length; j++) {
                if(arr[j] < arr[index]) {
                    index = j;
                }
            }
            temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
        return arr;
    }
}
