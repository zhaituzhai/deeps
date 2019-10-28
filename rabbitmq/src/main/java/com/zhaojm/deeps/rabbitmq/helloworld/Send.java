package com.zhaojm.deeps.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * We'll call our message publisher (sender) Send and our message consumer (receiver) Recv.
 * The publisher will connect to RabbitMQ, send a single message, then exit.
 */
public class Send {

    private static Logger log = LoggerFactory.getLogger(Send.class);

    private static final String QUEUE_NAME = "hello_world";

    public static void main(String[] args) {
        // 创建连接工厂
        ConnectionFactory factory = BaseUtil.getConnectionFactory();
        // 建立到代理服务器到连接
        // 获得信道
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            // 声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            // 发送信息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            log.debug(" [x] Sent '{}'", message);
        } catch (Exception e) {
            log.error("queue Declare error", e);
        }
    }


}
