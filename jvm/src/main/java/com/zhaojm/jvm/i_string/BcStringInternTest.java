package com.zhaojm.jvm.i_string;

/**
 * @author zhaojm
 * @date 2020/5/25 21:44
 */
public class BcStringInternTest {
    public static void main(String[] args) {
        String s = "ab";  //或者与第二行交互
        String s1 = new String("a") + new String("b");
        s1.intern();
        String s2 = "ab";
        System.out.println(s1 == s2);
    }
}
