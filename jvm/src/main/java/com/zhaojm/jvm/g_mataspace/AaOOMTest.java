package com.zhaojm.jvm.g_mataspace;


import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * 1.8
 * -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
 * @author zhaojm
 * @date 2020/5/17 19:51
 */
public class AaOOMTest extends ClassLoader {
    public static void main(String[] args) {
        int j = 0;
        try {
            AaOOMTest test = new AaOOMTest();
            for (int i = 0; i < 10000; i++) {
                ClassWriter classWriter = new ClassWriter(0);
                // 指明版本号，修饰符，类名，包名，父类，接口
                classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Class" + i, null, "java.lang.Object", null);
                // 返回 byte[]
                byte[] code = classWriter.toByteArray();
                test.defineClass("Class" + i, code, 0, code.length);
                j++;
            }
        } finally {
            System.out.println(j);
        }
    }
}
