package com.zhaojm.deeps.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;

public class BaseUtil {

    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 设置 RabbitMQ 地址
        factory.setHost("localhost");
        return factory;
    }
}
