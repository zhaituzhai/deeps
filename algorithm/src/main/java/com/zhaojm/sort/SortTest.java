package com.zhaojm.sort;

import java.util.Arrays;

public class SortTest {

    public static void main(String[] args) {
        int[] arr = new int[]{8,9,10,11,24,56,55,44,43,42,23,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
        /*System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(new BubbleSort().sort(arr)));*/
        /*System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(new SelectionSort().sort(arr)));*/
        /*System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(new InsertionSort().sort(arr)));*/
        /*System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(new ShellSort().sort(arr)));*/
        /*System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(new MergeSort().sort(arr)));*/
        /*System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(new QuickSort().sort(arr)));*/
        arr = new int[]{8,6,4,2,1,3,5,7,9};
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(new HeapSort().sort(arr)));
    }

}
