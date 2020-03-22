package com.zhaojm.basal.javaio.buffer;

import java.io.*;

public class BufferedInputFile {

    /**
     * 缓冲流读文件
     * @param filename
     * @return
     * @throws Exception
     */
    public static String read(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = br.readLine()) != null){
            sb.append(s + "\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(read("E:\\study\\testfile\\test.txt"));
        formattedMemoryInput();
    }

    /**
     * 从内存中度文件
     * @throws Exception
     */
    public static void memoryInput() throws Exception{
        StringReader sr = new StringReader(read("E:\\study\\testfile\\test.txt"));
        int c;
        while ((c = sr.read()) != -1){
            System.out.print((char) c);
        }
    }

    /**
     * 格式化的内存输入
     * @throws Exception
     */
    public static void formattedMemoryInput() throws Exception {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(read("E:\\study\\testfile\\test.txt").getBytes()));
//            while (true) {
            while (in.available() != 0) {  // available() 在没有阻塞的情况下能读取的字节数
                System.out.print((char) in.readByte());
            }
        } catch (Exception e) {
            System.err.println("End of stream");
        }
    }

}
