package com.zhaojm.sort;

public class MergeSort implements ISort {
    @Override
    public int[] sort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
        return arr;
    }

    public void mergeSort(int[] arr, int start, int end) {
        if (start >= end) return;
        int length = end - start, mid = (length >> 1) + start;
        int start1 = start;
        int start2 = mid + 1;
        mergeSort(arr, start1, mid);
        mergeSort(arr, start2, end);

        int[] temp = new int[length + 1];
        int step = 0;
        while (start1 <= mid && start2 <= end) {
            temp[step++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
        }

        while (start1 <= mid) {
            temp[step++] = arr[start1++];
        }
        while (start2 <= end) {
            temp[step++] = arr[start2++];
        }

        step = 0;
        for (int i = start; i <= end; i++) {
            arr[i] = temp[step++];
        }
    }
}
