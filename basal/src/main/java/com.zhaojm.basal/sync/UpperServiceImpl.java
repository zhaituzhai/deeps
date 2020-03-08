package com.zhaojm.basal.sync;

public class UpperServiceImpl implements UpperService {
    private BottomService bottomService;

    public UpperServiceImpl(BottomService bottomService) {
        this.bottomService = bottomService;
    }

    @Override
    public void upperTaskAfterCallBottomService(String upperParam) {
        System.out.println(upperParam + " upperTaskAfterCallBottomService() execute.");
    }
    @Override
    public String callBottomService(final String param) {
        return bottomService.bottom(param + " callBottomService.bottom() execute --> ");
    }
}
