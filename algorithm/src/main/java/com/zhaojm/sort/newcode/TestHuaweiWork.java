package com.zhaojm.sort.newcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TestHuaweiWork {

    public static void main(String[] args) throws IOException {
        mathAddInput2();
    }

    public static void mathAddInput2() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        while ((str = br.readLine()) != null) {
            if (str.equals("0")) {
                break;
            }
            String[] nums = str.split(" ");
            int ret = 0;
            for (int i = 0; i < nums.length; i++) {
                ret += Integer.parseInt(nums[i]);
            }
            System.out.println(ret);
        }
    }

    public static void mathAddInput1() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int times = Integer.parseInt(st.nextToken());
        for (int i = 0; i < times; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            System.out.println(a + b);
        }
    }


    public static void mathAddInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        while ((str = br.readLine()) != null) {
            String[] nums = str.split(" ");
            if (nums.length == 2) {
                int a = Integer.parseInt(nums[0]);
                int b = Integer.parseInt(nums[1]);
                System.out.println(a + b);
            }
        }
    }


}
