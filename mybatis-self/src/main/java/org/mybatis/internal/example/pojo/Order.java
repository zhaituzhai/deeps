package org.mybatis.internal.example.pojo;

/**
 * @author zhaojm
 * @date 2020-04-01 16:53
 */
public class Order {
    private String orderId;
    private String orderCode;
    private String ip;

    public Order() {
    }

    public Order(String orderId, String orderCode, String ip) {
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.ip = ip;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
