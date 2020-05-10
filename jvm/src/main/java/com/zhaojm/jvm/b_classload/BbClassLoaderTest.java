package com.zhaojm.jvm.b_classload;

import sun.security.ec.CurveDB;

import java.net.URL;
import java.security.Provider;

/**
 * @author zhaojm
 * @date 2020/5/10 10:56
 */
public class BbClassLoaderTest {

    public static void main(String[] args) {
        System.out.println("********启动类加载器*********");
        // 获取 BootstrapClassLoader 能够加载的 api 的路径
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url.toExternalForm());
        }
        // 从上面的路径种随意选择一个类，查看类加载器
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println(classLoader);


        System.out.println("*******扩展类加载器***********");
        String extDirs = System.getProperty("java.ext.dirs");
        for (String path : extDirs.split(";")) {
            System.out.println(path);
        }
        ClassLoader classLoader1 = CurveDB.class.getClassLoader();
        System.out.println(classLoader1);
    }

}
