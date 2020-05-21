package com.zhaojm.jvm.h_directmemory;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 *
 * -Xmx20m -XX:MaxDirectMemorySize=10m
 *
 * @author zhaojm
 * @date 2020/5/22 1:15
 */
public class AcDirectOOMTest {
    private static final long _1MB = 1024 * 1024 * 1;
    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe)unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
