package com.zhaojm.jvm.b_classload;

/**
 * @author zhaojm
 * @date 2020/5/10 10:01
 */
public class AdClassInitTest {

    // 任何类申明以后，内部至少存在一个类的构造器

    private  int a = 1;

    /**
     * 如果没有 static 属性或方法时，则不存在  <clinit>() 方法
     */
    private static int c = 3;

    public static void main(String[] args) {
        int b = 2;
    }

}
