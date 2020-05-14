package com.zhaojm.jvm.d_stack;

/**
 * 1. 每个栈帧内部都包含一个指向运行时常量池中该栈帧所属方法的引用。包含这个引用的目的就是为了支持当前方法的代码能够实现动态链接。
 * 2. 在Java源文件被编译到字节码文件中时，所有的变量合肥方法引用都作为符号引用保存在class文件的常量池里。比如：描述一个方法调用了
 * 另外的其他方法时，就是通过常量池中指向方法的符号引用来表示，那么动态链接的作用就是为了将这些符号转换在调用方法的直接引用。
 * @author zhaojm
 * @date 2020/5/13 22:32
 */
public class CaDynamicLinkingTest {
    int num = 10;
    public void methodA() {
        System.out.println("methodA()...");
    }

    public void methodB() {
        System.out.println("methodB()...");
        methodA();
        num++;
    }

    /*

     0: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
     3: ldc           #6                  // String methodB()...
     5: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     8: aload_0
     9: invokevirtual #7                  // Method methodA:()V  调用 a 方法 -> 调用虚方法 -> 运行时常量
    12: aload_0
    13: dup
    14: getfield      #2                  // Field num:I
    17: iconst_1
    18: iadd
    19: putfield      #2                  // Field num:I
    22: return



     */
}
