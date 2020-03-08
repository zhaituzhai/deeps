package com.zhaojm.basal.sync;

public interface UpperService {
    void upperTaskAfterCallBottomService(String upperParam);

    String callBottomService(final String param);
}
