package com.zhaojm.jvm.i_string;

/**
 * @author zhaojm
 * @date 2020/5/25 21:26
 */
public class BaStringInternTest {
    public static void main(String[] args) {
        String s3 = new String("1") + new String("1");
        String s4 = "11";
        String s5 = s3.intern();
        System.out.println(s3 == s4); // false
        System.out.println(s5 == s4); // true
    }
}
