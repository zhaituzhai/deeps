package com.zhaojm.basal.javaio.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GetChannel {
    private static final int BSIZE = 1024;

    static String filename = "E:\\study\\testfile\\data.txt.txt";

    /**
     * 对于所展示的任何流 getChannel() 将会产生一个fileChannel
     * 通道是一种相当基础的东西：可以向它传送用于读写的 ByteBuffer 并且可以锁定文的某些区域用于独占式访问
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        FileChannel fc = new FileOutputStream(filename).getChannel();
        // 将字节放入bytebuffer
        fc.write(ByteBuffer.wrap("some text ".getBytes()));
        fc.close();
        fc = new RandomAccessFile(filename, "rw").getChannel();
        // 定位到文件最后
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap("some more".getBytes()));
        fc.close();
        fc = new FileInputStream(filename).getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        fc.read(buff);
        // 调用 filp() 让它做好
        buff.flip();
        while (buff.hasRemaining()){
            System.out.print(((char) buff.get()));
        }
    }

}
