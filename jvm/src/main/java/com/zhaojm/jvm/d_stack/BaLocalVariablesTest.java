package com.zhaojm.jvm.d_stack;

import java.util.Date;

/**
 * 局部变量表所需的容量大小是在编译期确定下来的，并保存在方法的Code属性的maximum local varibales 数据项中。
 * 在方法运行期间是不会改变局部变量表的大小的。
 * @author zhaojm
 * @date 2020/5/12 21:56
 */
public class BaLocalVariablesTest {
    private int count = 0;

    public static void main(String[] args) {
        BaLocalVariablesTest test = new BaLocalVariablesTest();
        int num = 10;
//        test.test1();
        test.test5();
    }


    public static void testStatic() {
        BaLocalVariablesTest test = new BaLocalVariablesTest();
        Date date = new Date();
        int count = 10;
        System.out.println(count);
    }

    private void test1() {
        Date date = new Date();
        String name1 = "zhaojm.com";
        String info = test2(date, name1);
        System.out.println(date + name1);
    }

    private String test2(Date dateP, String name2) {
        dateP = null;
        name2 = "matte";
        double weight = 150;
        char gender = '男';
        return dateP + name2;
    }

    private void test3() {
        // 如果当前帧时由构造方法或实例方法创建的，那么该对象引用this将会放到index为0的slot处，其余的参数按照参数表顺序继续排列。
        count++;
    }

    private void test4() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }
        // 变量c使用之前已经销毁的变量b占据的slot的位置
        int c = a + 1;
    }
    private int hello;
    public void test5() {
        int num;
        System.out.println(hello);
//        System.out.println(num); // 错误信息未进行初始化
    }

}
