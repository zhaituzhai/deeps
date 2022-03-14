package com.zhaojm.sort.newcode;

import java.util.*;

/**
 * 从一个字符串中找到第一个不重复的字符，返回它的索引。如果不存在，则返回 -1。
 * 案例:
 * s = "qwert"
 * 返回 0.
 * s = "testengine",
 * 返回 2.
 * s = "helloolleh",
 * 返回 -1.
 * s = "abababe",
 * 返回 6.
 * 注意：字符串中只会包含小写字母。
 * class Solution {
 *     public int firstUniqueChar(String s) {
 *
 *     }
 * }
 */
public class HuaWeiInterview {

    public static void main(String[] args) {
        System.out.println(firstUniqueChar("testengine"));
    }

    public static int firstUniqueChar(String s) {
        if (s.length() <= 0)
            return -1;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
            } else {
                map.put(s.charAt(i), 1);
            }
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }

}
