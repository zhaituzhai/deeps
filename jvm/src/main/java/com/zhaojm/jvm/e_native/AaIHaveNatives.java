package com.zhaojm.jvm.e_native;

/**
 * 本地方法
 *
 * 为什么需要
 *
 * @author zhaojm
 * @date 2020/5/14 22:40
 */
public class AaIHaveNatives {
    public native void native1(int x);
    public native static long native2(int x);
    public native synchronized float native3(Object obj);
    public native void native4(int[] arr) throws Exception;
}
