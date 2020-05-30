package com.zhaojm.deeps.sorts;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * 我们将讨论三种基于比较的排序算法
 *
 * 冒泡排序
 * 选择排序
 * 插入排序
 * 它们被称为基于比较的比较，因为它们比较数组的元素对并决定是否交换它们。
 * 这三种排序算法最容易实现，但不是最有效的，因为它们的时间复杂度是O（N2)。
 *
 * @author zhaojm
 */
public class AaBubbleSort {
    private static final int[] NUMS = new int[]{3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

    public static void main(String[] args) {
        int length = NUMS.length;
        int num;
        // 第一个循环记录当次需要排序的个数
        for (int i = 0; i < length; i++) {
            // 第二个循环比较 0 到 length-i-1 的位置的至，每次把最大的值一到最后去
            for (int j = 0; j < length - i - 1; j++) {
                if (NUMS[j] > NUMS[j + 1]){
                    num = NUMS[j];
                    NUMS[j] = NUMS[j + 1];
                    NUMS[j + 1] = num;
                }
            }
        }
        System.out.println(Arrays.toString(NUMS));
    }
}
