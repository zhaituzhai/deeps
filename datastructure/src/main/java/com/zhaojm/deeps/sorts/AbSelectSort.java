package com.zhaojm.deeps.sorts;

import java.util.Arrays;

/**
 * 选择排序
 * @author zhaojm
 */
public class AbSelectSort {
    private static final int[] NUMS = new int[]{3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

    public static void main(String[] args) {
        int num;
        for (int i = 0; i < NUMS.length-1; i++) {
            for (int j = i; j < NUMS.length; j++) {
                if(NUMS[j] < NUMS[i]) {
                    num = NUMS[j];
                    NUMS[j] = NUMS[i];
                    NUMS[i] = num;
                }
            }
        }
        System.out.println(Arrays.toString(NUMS));
    }

}
