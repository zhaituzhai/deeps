package com.zhaojm.jvm.j_proxy.impl;

import com.zhaojm.jvm.j_proxy.IPrint;

public class SystemMsgPrintProxy implements IPrint {
    private SystemMsgPrint print;

    public SystemMsgPrintProxy() {
        this.print = new SystemMsgPrint();
    }

    @Override
    public void printMsg() {
        System.out.println("记录接口信息日志-静态代理");
        print.printMsg();
    }
}
