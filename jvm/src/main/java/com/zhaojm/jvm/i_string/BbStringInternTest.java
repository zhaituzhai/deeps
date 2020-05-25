package com.zhaojm.jvm.i_string;

/**
 * @author zhaojm
 * @date 2020/5/25 21:35
 */
public class BbStringInternTest {
    public static void main(String[] args) {
        //第二部
        String x = "ab";  // true /false
        // 第一步
        String s = new String("a") + new String("b");
        String s2 = s.intern();
        System.out.println(s2 == "ab"); // jdk6: true // jdk8:true
        System.out.println(s == "ab"); // jdk6: false // jdk8:true
    }
}
