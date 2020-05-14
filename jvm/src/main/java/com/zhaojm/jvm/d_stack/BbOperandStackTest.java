package com.zhaojm.jvm.d_stack;

/**
 * 操作数栈
 * @author zhaojm
 * @date 2020/5/12 23:20
 */
public class BbOperandStackTest {
    public void testAddOperation() {
        byte i = 15;
        int j = 8;
        int k = i + j;
    }
    /*

     0: bipush        15    --> 15 入操作数栈
     2: istore_1            --> 存储到局部变量表中1位置上
     3: bipush        8     --> 8 入操作数栈
     5: istore_2            --> 存储到局部变量表中2位置上
     6: iload_1             --> 读取局部变量表中1位置的值入栈
     7: iload_2             --> 读取局部变量表中2位置的值入栈
     8: iadd                --> 栈中两个值相加
     9: istore_3            --> 存储到局部变量表3的位置上
    10: return              --> 返回


     Code:
          stack=2, locals=4, args_size=1

    局部变量表 4  this,i,j,k
    操作数栈深度 2

    如果有返回值的话
     */

    public int getSum() {
        int m = 10;
        int n = 20;
        int k = m + n;
        return k;
    }

    public void testGetSum() {
        int i = getSum();
        int j = 10;
    }

    public void add() {
        // 1
        int i1 = 10;
        i1++;

        int i2 = 10;
        ++i2;

        // 2
        int i3 = 10;
        int i4 = i3++;

        int i5 = 10;
        int i6 = ++i5;

        // 3
        int i7  = 10;
        i7 = i7++;

        int i8 = 10;
        i8 = ++i8;

        // 4
        int i9 = 10;
        int i10 = i9++ + ++i9;
    }

    /*

     0: bipush        10
     2: istore_1
     3: iinc          1, 1
     6: bipush        10
     8: istore_2
     9: iinc          2, 1
    12: bipush        10
    14: istore_3
    15: iload_3
    16: iinc          3, 1
    19: istore        4
    21: bipush        10
    23: istore        5
    25: iinc          5, 1
    28: iload         5
    30: istore        6
    32: bipush        10
    34: istore        7
    36: iload         7
    38: iinc          7, 1
    41: istore        7
    43: bipush        10
    45: istore        8
    47: iinc          8, 1
    50: iload         8
    52: istore        8
    54: bipush        10
    56: istore        9
    58: iload         9
    60: iinc          9, 1
    63: iinc          9, 1
    66: iload         9
    68: iadd
    69: istore        10
    71: return



     */
}
