package com.zhaojm.sort.newcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestCode {

    /**
     * 废瓶子喝饮料   3个空瓶子换一瓶
     */
    public static void changeDrive(){
        Scanner in = new Scanner( System.in );
        while (in.hasNextLine()) {
            int num = Integer.parseInt(in.nextLine());
            if (num == 0) {
                break;
            }
            int result = 0, temp;
            while (num / 3 > 0) {
                temp = num / 3;
                num = temp + num % 3;
                result += temp;
            }
            if (num == 2) result = result + 1;
            System.out.println(result);
        }
    }

    public static int[] changeDrive(int[] nums) {
        int[] results = new int[nums.length - 1];
        int result = 0, temp;
        for (int i = 0; i < nums.length; i++) {
            int num;
            if ((num = nums[i]) == 0) {
                return results;
            }
            while (num / 3 > 0) {
                temp = num / 3;
                num = temp + num % 3;
                result += temp;
            }
            if (num == 2) result = result + 1;
            results[i] = result;
        }
        return results;
    }

    public static void testRoundNums() {
        Scanner in = new Scanner(System.in);
        int start = 0;
        while (in.hasNextLine()) {
            start++;
            int n = in.nextInt(), index = 0;
            int[] nums = new int[n];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = in.nextInt();
                index++;
            }
            Arrays.sort(nums, start, index);
            for (int i = start; i < index; i++) {
                if(i == start || nums[i] != nums[i - 1]) {
                    System.out.println(nums[i]);
                }
            }
            start = index;
        }
    }


    public static void testSortNum(int[] nums) {
        int start = 0, end = 0;
        for (int i = 0; i < nums.length; i++) {
            int len = nums[start];
            // int[] tempNums = new int[len];
            start++;
            end = start + len - 1;
            Arrays.sort(nums, start, end + 1);
            for (int j = start; j <= end; j++) {
                if(j == start || nums[j] != nums[j - 1]) {
                    System.out.println(nums[j]);
                }
            }
            start = end + 1;
            i = end;
        }
    }

    public static void outHex(String str){
        System.out.println(Integer.decode(str));
    }

    public static void main(String[] args) {
        // testSortNum(new int[]{3,5,5,3,4,7,7,8,9,2,5,3,4,3,3,5,7});
        outHex("#43a");
    }

}
class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] nums = new int[1024];
        int index = 0;
        while (in.hasNext()) {
            nums[index++] = in.nextInt();
        }
        int start = 0, end = 0;
        for (int i = 0; i < nums.length; i++) {
            int len = nums[start];
            start++;
            end = start + len - 1;
            Arrays.sort(nums, start, end + 1);
            for (int j = start; j <= end; j++) {
                if(j == start || nums[j] != nums[j - 1]) {
                    System.out.println(nums[j]);
                }
            }
            start = end + 1;
            i = end;
        }
    }
}