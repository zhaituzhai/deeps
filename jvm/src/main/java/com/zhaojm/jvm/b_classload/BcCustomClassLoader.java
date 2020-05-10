package com.zhaojm.jvm.b_classload;

import java.io.FileNotFoundException;

/**
 * @author zhaojm
 * @date 2020/5/10 15:25
 */
public class BcCustomClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] result = getClassFromCustomPath(name);
            if(null == result) {
                throw new FileNotFoundException();
            } else {
                return defineClass(name, result, 0, result.length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        throw new ClassNotFoundException(name);
    }

    /**
     * 可以继承 URLClasaLoader 简化操作
     * @param name
     * @return
     */
    private byte[] getClassFromCustomPath(String name) {
        // 从自定义路径中加载指定类：
        // 如果指定字节码文件加密，则可以在此方法中进行jxmi操作
        return null;
    }

    public static void main(String[] args) {
        BcCustomClassLoader customClassLoader = new BcCustomClassLoader();

    }

}
