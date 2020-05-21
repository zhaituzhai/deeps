package com.zhaojm.jvm.h_directmemory;

import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * @author zhaojm
 * @date 2020/5/22 0:40
 */
public class AaBufferTest {

    private static final int BUFFER = 1024*1024*1024; // 1GB

    public static void main(String[] args) {
        // allocateDirect 直接分配本地内存空间
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER);
        System.out.println("内存分配完毕。。。。");
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        System.out.println("直接释放内存。。。");
        byteBuffer = null;
        System.gc();
        scanner.next();
    }
}
