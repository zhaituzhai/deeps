package com.zhaojm.deeps.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.zhaojm.deeps.rabbitmq.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * That's it for our publisher. Our consumer listening for messages from RabbitMQ, so unlike the publisher
 * which publishes a single message, we'll keep it running to listen for messages and print them out.
 * 消费者
 *
 */
public class Recv {

    private static Logger log = LoggerFactory.getLogger(Recv.class);

    private static final String QUEUE_NAME = "hello_world";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = BaseUtil.getConnectionFactory();
        // 获取连接以及mq信道
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        log.info(" [*] Waiting for messages. To exit press CTRL+C");
        // 声明消费者 获取消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.debug(" [x] Received '{}'", message);
        };
        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }
}