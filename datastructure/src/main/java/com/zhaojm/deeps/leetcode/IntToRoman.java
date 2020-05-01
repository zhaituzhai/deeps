package com.zhaojm.deeps.leetcode;

/**
 * @author zhaojm
 * @date 2020/4/28 22:36
 */
public class IntToRoman {

    public static void main(String[] args) {
        System.out.println(intToRoman(199));
    }

    public int[] singleNumbers(int[] nums) {

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum ^= nums[i];
        }

//        while ()

        return null;
    }

    public static String intToRoman(int num) {
        StringBuffer sb = new StringBuffer();
        while (num > 0) {
            if(num >= 1000) {
                sb.append("M");
                num -= 1000;
            } else if(num >= 900) {
                sb.append("CM");
                num -= 900;
            } else if(num >= 500) {
                sb.append("D");
                num -= 500;
            } else if(num >= 400) {
                sb.append("CD");
                num -= 400;
            } else if(num >= 100) {
                sb.append("C");
                num -= 100;
            } else if(num >= 90) {
                sb.append("XC");
                num -= 90;
            } else if(num >= 50) {
                sb.append("L");
                num -= 50;
            } else if(num >= 40) {
                sb.append("XL");
                num -= 40;
            } else if(num >= 10) {
                sb.append("X");
                num -= 10;
            } else if(num >= 9) {
                sb.append("IX");
                num -= 9;
            } else if(num >= 5) {
                sb.append("V");
                num -= 5;
            } else if(num >= 4) {
                sb.append("IV");
                num -= 4;
            } else {
                sb.append("I");
                num -= 1;
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

}
