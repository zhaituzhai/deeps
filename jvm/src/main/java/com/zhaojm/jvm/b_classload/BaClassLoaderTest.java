package com.zhaojm.jvm.b_classload;

/**
 * @author zhaojm
 * @date 2020/5/10 10:31
 */
public class BaClassLoaderTest {

    public static void main(String[] args) {

        // 获取系统类加载器
        ClassLoader systemCLassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemCLassLoader); // sun.misc.Launcher$AppClassLoader@18b4aac2

        // 获取qi其上层类加载器
        ClassLoader extClassLoader = systemCLassLoader.getParent();
        System.out.println(extClassLoader); // sun.misc.Launcher$ExtClassLoader@1b6d3586

        // 试图获取 bootstrap类加载器
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println(bootstrapClassLoader); // null

        // 用于自定义类
        ClassLoader userLoader = BaClassLoaderTest.class.getClassLoader();
        System.out.println(userLoader); // sun.misc.Launcher$AppClassLoader@18b4aac2 与扩展类一致

        // String 类使用的引导类加载 --> Java 的核心类库是使用引导类加载器进行加载的。
        ClassLoader classLoader = String.class.getClassLoader();
        System.out.println(classLoader);

    }}
