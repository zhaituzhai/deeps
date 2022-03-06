package com.zhaojm.sort;

public class ShellSort implements ISort {
    @Override
    public int[] sort(int[] arr) {
        int length = arr.length;
        int temp;
        for (int gap = length / 2; gap > 0; gap = gap / 2 ) {
            for (int i = 0; i < length - gap; i = i + gap) {
                for (int j = i + gap; j > 0; j = j - gap) {
                    if (arr[j] < arr[j - gap]) {
                        temp = arr[j - gap];
                        arr[j - gap] = arr[j];
                        arr[j] = temp;
                    }
                }
            }
        }
        return arr;
    }
}
