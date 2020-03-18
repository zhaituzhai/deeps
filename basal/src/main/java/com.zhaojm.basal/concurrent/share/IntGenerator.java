package com.zhaojm.basal.concurrent.share;

public abstract class IntGenerator {
    private volatile boolean canceled = false;
    public abstract int next();

    // 允许取消
    public void cancel(){
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
