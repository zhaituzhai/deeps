package com.zhaojm.jvm.j_proxy.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyHandler implements InvocationHandler {
    private Object targetObject; // 被代理的对象

    public  Object newProxyInstance(Object targetObject) {
        this.targetObject = targetObject;
        // 第一个参数：targetObject.getClass().getClassLoader() 目标类加载器
        // 第二个参数：targetObject.getClass().getInterfaces()  目标对象的所有接口
        // 第三个参数： this 当前对象。在调用方法时会调用它的invoke方法。
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("记录接口信息日志-动态代理");
        return method.invoke(targetObject, args);
    }
}
