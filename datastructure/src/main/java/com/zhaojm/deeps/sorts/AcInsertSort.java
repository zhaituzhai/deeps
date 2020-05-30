package com.zhaojm.deeps.sorts;

import java.util.Arrays;

/**
 * 插入排序
 * @author zhaojm
 */
public class AcInsertSort {
    private static final int[] NUMS = new int[]{3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

    public static void main(String[] args) {
        int num;
        for (int i = 1; i < NUMS.length; i++) {
            for (int j = i; j > 0; j--) {
                if (NUMS[j] < NUMS[j - 1]) {
                    num = NUMS[j];
                    NUMS[j] = NUMS[j - 1];
                    NUMS[j - 1] = num;
                }
            }
        }
        System.out.println(Arrays.toString(NUMS));
    }
}
