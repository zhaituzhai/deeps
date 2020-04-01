package org.mybatis.internal.example.pojo;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * @author zhaojm
 * @date 2020-04-01 15:31
 */
public class Mobile {
    private String mobile;

    private Mobile() {}

    public Mobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
