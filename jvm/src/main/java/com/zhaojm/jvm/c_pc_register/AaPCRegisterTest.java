package com.zhaojm.jvm.c_pc_register;

/**
 * PC 寄存器 PC寄存器用来存储指向下一条指令的地址，也即将要执行的指令代码。由执行引擎读取下一条指令。
 * @author zhaojm
 * @date 2020/5/11 21:49
 */
public class AaPCRegisterTest {
    public static void main(String[] args) {
        int i = 10;
        int j = 20;
        int k = i + j;
        String s = "abc";
        System.out.println(i);
        System.out.println(s);
    }
}
