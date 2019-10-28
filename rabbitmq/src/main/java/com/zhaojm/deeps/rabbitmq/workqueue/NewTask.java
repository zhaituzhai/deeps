package com.zhaojm.deeps.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.zhaojm.deeps.rabbitmq.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class NewTask {

    private static Logger log = LoggerFactory.getLogger(NewTask.class);

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = BaseUtil.getConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // durable为true时,队列在服务重新启动后，还继续存活。
            // exclusive：表示该队列只限于当前这个连接。
            // autoDelete：当这个队列不再被使用时，server将删除它。
            // arguments: 队列的其他属性，构造参数
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                String message = String.join(" " + i, argv);

                channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
                log.debug(" [x] Sent '{}'", message);
            }
        }
    }
}
