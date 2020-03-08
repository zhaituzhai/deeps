package com.zhaojm.basal.javaio;

import java.io.*;

public class StreamTest {

    public static void main(String[] args) {
        testRandomAccessStream();
    }

    public static void testRandomAccessStream(){
        try {
            FileInputStream fis = new FileInputStream(new File("E:\\study\\testfile\\test.txt"));
            FileOutputStream fos = new FileOutputStream(new File("E:\\study\\testfile\\testCopy.txt"));
            byte[] buffer = new byte[128];
            while (fis.read(buffer) != -1 ){
                fos.write(buffer);
            }
            //随机读写，通过mode参数来决定读或者写
            RandomAccessFile raf = new RandomAccessFile(new File("E:\\study\\testfile\\testCopyAc.txt"), "rw");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
