package com.zhaojm.jvm.i_string;

/**
 * @author zhaojm
 * @date 2020/5/24 22:04
 */
public class AaStringTest {
    public static void main(String[] args) {
        test1();

    }

    String str = new String("good");
    char[] ch = {'t', 'e', 's', 't'};

    private static void test2(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'b';
    }

    private static void test1() {
        String s1 = "abc";
        String s2 = "abc";
        System.out.println(s1 == s2);
        s2 = "hello";
        System.out.println(s1 == s2);
    }
}
