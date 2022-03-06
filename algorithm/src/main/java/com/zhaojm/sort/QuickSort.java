package com.zhaojm.sort;

public class QuickSort implements ISort {
    @Override
    public int[] sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }

    private void quickSort(int[] arr, int left, int right) {
        int temp;
        int pivot = arr[(left + right) / 2];
        int i = left, j = right;
        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }
            if (i <= j) {
                temp = arr[i];
                arr[i++] = arr[j];
                arr[j--] = temp;
            }
        }
        if (left < i - 1) {
            quickSort(arr, left, i - 1);
        }
        if (i < right) {
            quickSort(arr, i, right);
        }
    }

    /*public void quickSort(int[] arr, int left, int right) {
        int temp;
        int pivot = arr[(left + right) / 2];
        int i = left, j = right;
        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }
            if(i <= j) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        if (left < i - 1) {
            quickSort(arr, left, i - 1);
        }
        if (i < right) {
            quickSort(arr, i, right);
        }
    }*/


}
