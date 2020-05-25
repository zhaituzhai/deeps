package com.zhaojm.deeps.leetcode;

import java.util.Stack;

/**
 *
 * 给你一幅由 N × N 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。
 *
 * 不占用额外内存空间能否做到？
 *
 * 给定 matrix =
 * [
 *   [1,2,3],
 *   [4,5,6],
 *   [7,8,9]
 * ],
 *
 * 原地旋转输入矩阵，使其变为:
 * [
 *   [7,4,1],
 *   [8,5,2],
 *   [9,6,3]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/rotate-matrix-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author zhaojm
 * @date 2020/4/7 21:26
 */
public class Rotate {
    public static void main(String[] args) {

    }

    public void test(String s) {
        StringBuffer str = new StringBuffer();
        Stack<Integer> temp = new Stack<>();
        char[] sp = s.toCharArray();
        for (int rigth = 0; rigth < sp.length; rigth++) {
            if(sp[rigth] == '[' || sp[rigth] == '|') {
                temp.push(rigth);
            }
            if(sp[rigth] == ']') {
                int k = temp.peek();
                temp.pop();
                int left = temp.peek();
                temp.pop();
                int num = new Integer(s.substring(k+1, k-left));
                String s1 = s.substring(k + 1, rigth - k - 1);
                String s2 = "";
                for (int i = 0; i < num; i++) {
                    s2 += s1;
                }
//                s = s.replace(left, rigth - left + 1, s2);
                rigth = left + s2.length() - 1;
            }
        }
    }
}
