package com.zhaojm.basal.javaio.buffer;

import java.io.RandomAccessFile;

public class UsingRandomAccessFile {

    static String file = "E:\\study\\testfile\\rtest.bat";
    static  void display() throws Exception {
        RandomAccessFile rf = new RandomAccessFile(file, "r");
        for (int i = 0; i < 7; i++) {
            System.out.println("value " + i + "i " + rf.readDouble());
        }
        System.out.println(rf.readUTF());
        rf.close();
    }

    public static void main(String[] args) throws Exception {
        RandomAccessFile rf = new RandomAccessFile(file, "rw");
        for (int i = 0; i < 7; i++) {
            rf.writeDouble(i * 1.414);
        }
        rf.writeUTF("The end of the file");
        rf.close();
        display();
        rf = new RandomAccessFile(file, "rw");
        rf.seek(5*8); // 查找第五个双精度的值
        rf.writeDouble(47.0001);
        rf.close();
        display();
    }

}
