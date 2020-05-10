package com.zhaojm.jvm.b_classload;

/**
 * 父类优于子类先被加载
 * @author zhaojm
 * @date 2020/5/10 10:07
 */
public class AeClassInitTest {
    static class Father{
        public static int A = 1;
        static {
            A = 2;
        }
    }
    static class Son extends Father {
        public static int B = A;
    }

    public static void main(String[] args) {
        System.out.println(Son.B);
    }
}
