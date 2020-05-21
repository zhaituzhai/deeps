package com.zhaojm.jvm.h_directmemory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * IO / NIO 比较
 *
 * @author zhaojm
 * @date 2020/5/22 0:50
 */
public class AbBufferCompareTest {
    private static final String TO = "";
    private static final int _100MB = 1024 * 1024 * 100;

    public static void main(String[] args) {
        long sum = 0;
        String src = "F:/test/test.mp4";
        for (int i = 0; i < 3; i++) {
            String dest = "F:/test/test_" + i + ".mp4";
            sum += io(src, dest);
            sum += directBuffer(src, dest);
        }
    }

    private static long io(String src, String dest) {
        long start = System.currentTimeMillis();
        try ( FileInputStream fis = new FileInputStream(src);
              FileOutputStream fos = new FileOutputStream(dest)){
            byte[] buffer = new byte[_100MB];
            while (true) {
                int len = fis.read(buffer);
                if (len == -1) {
                    break;
                }
                fos.write(buffer, 0, len);
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return System.currentTimeMillis() - start;
    }

    private static long directBuffer(String src, String dest) {
        long start = System.currentTimeMillis();

        try(FileChannel inChannel = new FileInputStream(src).getChannel();
            FileChannel outChannel = new FileOutputStream(dest).getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_100MB);
            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip(); //修改为都数据模式
                outChannel.write(byteBuffer);
                byteBuffer.clear(); //清空
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return System.currentTimeMillis() - start;
    }

}
