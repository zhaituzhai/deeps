package com.zhaojm.jvm.j_proxy.impl;

import com.zhaojm.jvm.j_proxy.IPrint;

public class SystemMsgPrint implements IPrint {

    @Override
    public void printMsg() {
        System.out.println("打印信息");
    }
}
