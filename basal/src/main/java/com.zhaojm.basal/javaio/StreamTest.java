package com.zhaojm.basal.javaio;

import java.io.*;
import java.util.Arrays;

public class StreamTest {

    public static void main(String[] args) throws Exception {
        testFile();
    }

    private static void testRandomAccessStream() {
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

    public static void testFile() throws Exception{
        String fileName = "E:\\study\\testfile\\test.txt";
        File file = new File(fileName);
        System.out.println(file.getPath());
        System.out.println(file.getName());
        System.out.println(file.getParent());
        // Arrays.stream(file.getParentFile().list()).forEach(System.out::println);
        System.out.println(file.isAbsolute());
        System.out.println(file.getCanonicalPath());
        System.out.println(file.toURI().getPath());

    }

}
