package com.zhaojm.deeps.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 实现一个算法，确定一个字符串 s 的所有字符是否全都不同。
 * 示例 1：
 *
 * 输入: s = "leetcode"
 * 输出: false
 * 示例 2：
 *
 * 输入: s = "abc"
 * 输出: true
 *
 * 限制：
 *
 * 0 <= len(s) <= 100
 * 如果你不使用额外的数据结构，会很加分。
 * @author zhaojm
 * @date 2020/4/6 22:36
 */
public class IsUnique {
    public static void main(String[] args) {
        String value = "行业 \r\n" +
                "分类";
        System.out.println(value);
        value = value.replaceAll("\r\n|\n", "");


        System.out.println(value);
    }

    public static boolean isUnique(String astr) {
        String[] split = astr.split("");
        Set<String> unique = new HashSet<>(split.length);
        for (int i = 0; i < split.length; i++) {
            unique.add(split[i]);
        }
        return split.length == unique.size();
//        Set<String> unique = new HashSet<>(split.length);
//        for (int i = 0,length = split.length; i < length; i++) {
//            if (unique.contains(split[i])){
//                return false;
//            } else {
//                unique.add(split[i]);
//            }
//        }
//        return true;
    }
}
