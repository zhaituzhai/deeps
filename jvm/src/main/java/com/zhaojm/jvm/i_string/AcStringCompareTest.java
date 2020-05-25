package com.zhaojm.jvm.i_string;

/**
 * @author zhaojm
 * @date 2020/5/25 21:11
 */
public class AcStringCompareTest {

    public static void main(String[] args) {
        String s = new String("1"); // 指向堆中的地址
        s.intern(); // 调用方法前，String 已经存在常量池中了。
        String s2 = "1"; // 常量池中地址
        System.out.println(s == s2); // jdk6/ jdk7 / jdk8 /  false

        String s3 = new String("1") + new String("1"); // s3地址为 new String("11")
        // 执行完上一行代码之后，字符串常量池中，是否存在 "11" ?  => 不存在！
        s3.intern(); // 在字符串常量池中生成11。  jdk 6：创建了一个新对象"11"，也就有新地址。
                                           //  jdk 7: 此时常量中并没有创建 "11",而是创建一个指向堆空间中new的地址
        String s4 = "11";  // S4 变量记录的地址：使用的是上一行代码执行时，在常量池中生成的 "11" 的地址。
        System.out.println(s3 == s4); // jdk 6 false  jdk 7/8 true
    }

}
