package com.zhaojm.deeps.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * @author zhaojm
 * @date 2020/4/7 21:31
 */
public class MaxArea {

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

    public static int maxArea(int[] height) {
        int head = 0, end = height.length -1;
        int area = 0;
        while (head < end) {
            area = Math.max(area, Math.min(height[head], height[end]) * (end - head));
            if (height[head] < height[end])
                head++;
            else
                end--;
        }
        return area;
    }

//     罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
//    I             1
//    V             5
//    X             10
//    L             50
//    C             100
//    D             500
//    M             1000
    public static Map<String, Integer> number = new HashMap<String, Integer>() {{
        put("I", 1);
        put("V", 5);
        put("X", 10);
        put("L", 50);
        put("C", 100);
        put("D", 500);
        put("M", 1000);
    }};


}
