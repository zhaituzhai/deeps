package com.zhaojm.jvm.j_proxy;

import com.zhaojm.jvm.j_proxy.impl.ProxyHandler;
import com.zhaojm.jvm.j_proxy.impl.SystemMsgPrint;
import com.zhaojm.jvm.j_proxy.impl.SystemMsgPrintProxy;

import java.lang.reflect.InvocationHandler;

public class AaStaticProxy {

    public static void main(String[] args) {
        /* // 普通调用方法
        SystemMsgPrint print = new SystemMsgPrint();
        print.printMsg();
        */

        /*// 静态代理方法
        SystemMsgPrintProxy proxy = new SystemMsgPrintProxy();
        proxy.printMsg();
        */
        // 动态代理
        // InvocationHandler handler =
        ProxyHandler proxyHandler = new ProxyHandler();
        IPrint print = (IPrint) proxyHandler.newProxyInstance(new SystemMsgPrint());
        print.printMsg();


    }

}
