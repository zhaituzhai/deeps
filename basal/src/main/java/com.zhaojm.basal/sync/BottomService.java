package com.zhaojm.basal.sync;

public class BottomService {
    public String bottom(String param) {
        try { //  模拟底层处理耗时，上层服务需要等待
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param + " BottomService.bottom() execute -->";
    }
}
