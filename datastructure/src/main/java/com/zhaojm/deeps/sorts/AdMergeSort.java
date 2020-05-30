package com.zhaojm.deeps.sorts;

/**
 * 归并排序
 * 我们将在接下来的几张幻灯片中讨论两种（加一半）基于比较的排序算法：
 *
 * 归并排序，
 * 快速排序和它的随机版本（只有一个变化）。
 * 这些排序算法通常以递归方式实现，使用分而治之的问题解决范例，并且运行在合并排序和O（N log N）时间的O（N log N）时间中，以期望随机快速排序。
 * PS：尽管如此，快速排序（Quick Sort）的非随机版本在 O(N2) 中运行。
 * @author zhaojm
 */
public class AdMergeSort {
    private static final int[] NUMS = new int[]{3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

    /**
    给定一个N个项目的数组，归并排序将：
    将每对单个元素（默认情况下，已排序）归并为2个元素的有序数组，
    将2个元素的每对有序数组归并成4个元素的有序数组，重复这个过程......，
    最后一步：归并2个N / 2元素的排序数组（为了简化讨论，我们假设N是偶数）以获得完全排序的N个元素数组。
    这只是一般的想法，在我们可以讨论归并排序的真正形式之前，我们需要更多的细节。
     */
    public static void main(String[] args) {

    }

    public static void mergeSort(int[] sort){
        int index = sort.length/2;
        if(index < 1){

        } else {

        }
    }

}
