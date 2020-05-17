package com.zhaojm.jvm.f_heap;

/**
 * @author zhaojm
 * @date 2020/5/17 17:35
 */
public class FaEscapeAnalysis {
    public FaEscapeAnalysis obj;

    /**
     * 方法返回 EscapeAnalysis 对象，发生逃逸
     * @return
     */
    public FaEscapeAnalysis getInstance() {
        return obj == null ? new FaEscapeAnalysis() : obj;
    }

    /**
     * 为成员变量赋值，发生逃逸
     */
    public void setObj() {
        this.obj = new FaEscapeAnalysis();
    }

    /**
     * 引用成员变量的值，发生逃逸
     */
    public void useEscapeAnalysis(){
        FaEscapeAnalysis e = getInstance();
    }

    /**
     * 对象作用域在当前方法中有效，没有逃逸
     */
    public void useEscapeAnalysis1(){
        FaEscapeAnalysis e = new FaEscapeAnalysis();
    }
}
