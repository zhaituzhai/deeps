package com.zhaojm.jvm.f_heap;

/**
 * JVM 参数设置
 * -XX:+PrintFlagsInitial ： 查看所有参数的默认值
 * -XX:+PrintFlagsFinal : 查看所有的参数的最终值（可能被修改）
 *      jps   / jinfo -flag SurvivorRation 进程id
 *
 * -Xms：初始堆空间内存（默认物理内存 1/64）
 * -Xmx：最大堆空间（默认物理内存1/4）
 * -Xmn：设置新生代大小。（初始值及最大值）
 * -XX:NewRatio：配置新生代与老年代在堆结构的占比
 * -XX:SurvivorRatio：配置新生代中Eden和S0/S1空间比例
 * -XX:MaxTenuringThreshold：设置进入老年代年龄阈值
 * -XX:+PrintGCDetails：输出详细GC处理日志  -XX:PrintGC   -verbose:gc
 * -XX:HandlePromotionFailure：设置空间分配担保
 * @author zhaojm
 * @date 2020/5/17 17:01
 */
public class EaJVMOptionalTest {
    public static void main(String[] args) {

    }
}
