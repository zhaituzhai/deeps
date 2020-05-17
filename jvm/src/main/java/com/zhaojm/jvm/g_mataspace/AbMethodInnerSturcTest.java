package com.zhaojm.jvm.g_mataspace;

import java.io.Serializable;

/**
 *
 * -p 私有权限
 * javap -v -p
 *
 * @author zhaojm
 * @date 2020/5/17 20:27
 */
public class AbMethodInnerSturcTest extends Object implements Comparable<String>, Serializable {

    public int num = 10;
    private static String str = "测试方法的内部结构";

    // 构造器
    // 方法
    public void test1() {
        int count = 20;
        System.out.println("count = " + count);
    }

    public static int test2(int cal) {
        int result = 0;
        try {
            int value = 30;
            result = value / cal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }
}
