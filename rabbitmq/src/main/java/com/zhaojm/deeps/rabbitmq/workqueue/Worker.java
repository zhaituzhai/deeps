package com.zhaojm.deeps.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.zhaojm.deeps.rabbitmq.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class Worker {

    private static Logger log = LoggerFactory.getLogger(Worker.class);

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = BaseUtil.getConnectionFactory();
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        log.info(" [*] Waiting for messages. To exit press CTRL+C");

        /* channel prefetch Setting（信道预取值设置）（QoS）由于消息是异步发送（推送）给客户端的，因此在任何给定时刻通常都有不止一条消息在信道上“正在运行”。
        此外，客户的手动确认本质上也是异步的。所以有一个未确认的交付标签的滑动窗口。开发人员通常会倾向于限制此窗口的大小，以避免消费者端无限制的缓冲区问题。
        这个缓冲区的大小是通过basicQos（）方法设置的。该值定义了通道上允许的最大未确认递送数量。一旦数字达到配置的计数，RabbitMQ将停止在通道上传送更多消息，
        除非至少有一个未确认的消息被确认。
        例如，假设在通道Ch上未确认的交付标签5,6,7和8 以及通道  Ch的预取计数设置为4，那么RabbitMQ将不会在Ch上推送更多交付，除非至少有一个未完成交付被承认。
        当确认帧在delivery_tag设置为8的频道上到达时 ，RabbitMQ将会注意到并再发送一条消息。
        值得注意的是，交付流程和消费者手动确认完全是异步的，暂时可能比预取计数通道上未确认的消息更多。

        消费者确认模式、预取（prefetch）、Throughput（吞吐量）
        确认模式和Qos预取值对消费者吞吐量有显著影响。
        一般来说，增加预取值量将提高向消费者传递信息的速度。
        自动确认模式可以产生最佳的传输效率.但在这两种情况下,交付但尚未处理的消息数量也会增加,从而增加了消费者内存的消耗.
        自动确认模式或带无限预取的手动确认模式应谨慎使用.
        消费者在没有确认的情况下,消耗大量的消息将导致其所连接的节点的内存消耗增长,寻找合适的预取值会应因工作负载而异.
        100到300范围内的值通常提供最佳的吞吐量，并且不会面临压倒性消费者的重大风险。更高的价值往往会遇到收益递减的规律1的预取值是最保守的。
        这将显着降低吞吐量，特别是在消费者连接延迟较高的环境中。对于许多应用来说，更高的价值是合适和最佳的。

        消费者失败或失去连接时：自动重新排队
        当使用手动确认时，当传输发生的通道（或连接）关闭时，没有被劫持的任何交付（消息）都会自动重新请求。
        这包括客户端TCP连接损失、消费者应用程序（进程）故障和通道级协议异常（如下所示）。
        需要注意的是:检测不可用客户端需要一段时间.
        由于这种行为,消费者一定要准备好处理重复传递以及以其他方式来实现幂等.再交付将有一个特殊的布尔属性:redeliver，
        由RabbitMQ 设置为true，首次delivery（发送）时，它将被设置为false，请注意，消费者可以收到先前传送给其他消费者的消息。
        如果客户端不止一次的确认相同的投递标签（DeliveryTag），RabbitMQ将给出一个通道（channel）错误，
        诸如PRECONDITION_FAILED - unknown delivery tag 100，如果使用未知的交付标签，则会抛出相同的通道异常。
        另一种情况下，RabbitMQ在抛出“未知的交付标记”，即不允许在不同channel上确认交付。

        生产者确认
        网络传输可能以不甚明显的方式失败，并且检测某些故障需要时间，因此，一个客户端编写了一个协议框架或一组框架（例如已发布的消息）到它的套接字，
        不能假设消息已经到达服务器并成功地处理了。它可能会在途中丢失或者它的交付会被显著延迟使用标准的AMQP协议，保证消息不会丢失的唯一方法是使用
        事务（即使我们的通道事务化），然后为每条消息或一组消息发布，提交。在这种情况下，事务是不必要的重量级，并且减少了250倍的吞吐量。
        为了解决这一问题，引入了一种确认机制。它模仿了协议中已经存在的消费者确认机制。
        要启用确认，客户端会调用confirmSelect（）方法，一旦在通道上使用confirmSelect（）方法，就说明它处于确认模式，事务通道不能进入确认模式，
        一旦通道进入确认模式，就不能进行事务处理了。
        一旦通道处于确认模式，代理和客户端都会对消息进行计数（在第一次confirm.select时从1开始计数 ）。
        然后，代理（RabbitMQ）通过在相同频道(channel)上发送basic.ack来处理消息，从而确认消息 。
        所述  输送标签字段包含确认消息的序列号。代理也可以在basic.ack中设置  多个字段，以指示所有消息直到并包括具有序列号的消息都已被处理。

        拒绝确认发布
        在特殊情况下,代理无法成功处理消息,而不是basicAck()（确认消息的发布），代理会发送一个basicNack()，在这种情况下，在基础上，nack具有
        与basicAck相应的含义相同的含义，ack和请求字段应该被忽略。
        通过发出一个或多个消息，代理表示无法处理消息，并拒绝对其负责;在这一点上，客户端可能会选择重新发布消息。

        当一个通道被放入确认模式后，所有随后发布的消息将被ack或nack一次。对于消息的确认时间，没有任何保证。任何信息都不会被确认，也不会被确认。
        basicNack只有在负责队列的Erlang进程中发生内部错误时，nack才会被交付。
        生产者何时发布消息？
        对于不可路由的消息，代理将在交换验证消息不会路由到任何队列（返回队列的空列表）时发出确认。
        如果消息也被作为强制发布，在basicAck之前basicReturn将被发送给客户端。对于basicNack（）也是如此。

        对于可路由消息，当消息被所有队列接受时发送basic.ack。对于路由到持久队列的持久消息，这意味着持久化到磁盘。对于镜像队列，这意味着所有镜像都接受了该消息。

        在大多数情况下，RabbitMQ将按发布顺序向发布商确认消息（这适用于在单个频道上发布的消息）。但是，发布者确认是异步发出的，并且可以确认一条消息或一组消息。
        发出确认的确切时刻取决于消息的传递模式（持久性与瞬态）以及消息被路由到的队列的属性（请参见上文）。也就是说不同的消息可以被认为准备好在不同的时间进行确认。
        这意味着，与各自的消息相比，确认可以以不同的顺序到达。应用程序应尽可能不取决于确认的顺序。该方法的prefetchCount参数定义了通道上允许的最大未确认投递数量。
        一旦数字达到配置的计数，RabbitMQ将停止在通道上传送更多消息，除非至少有一个未确认的消息被确认。
        该值（prefetchCount）为100-300提供最佳的吞吐量。
        如果该值为1，它是最为保险的，这将显著降低吞吐量，特别是在消费者连接延迟较高的环境下。
        对于许多应用来说，更高的值是合适的和最佳的。
        channel.confirmSelect()
        channel.basicNack();
        channel.basicGet();
        */
        // 同一时刻，服务器只会发一条消息给消费者
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.debug(" [x] Received '{}'", message);
            /*下面的getDeliveryTag（）将获得投递标签（DeliveryTag）。下面说说这个标签的由来：
            当一个消费者向rabbitMQ注册后，将通过RabbitMQ使用basic递送方法发送（推）消息，该方法携带一个递送标签，它唯一地标识一个通道上的传输。
            因此每个通道的交付标记都是限定范围的。如果multiple为true，RabbitMQ将确认所有未完成交付标签，甚至包括确认中指定的标签，与其他所有与
            确认相关的内容一样，这是每个频道的范围。
            例如，假设在通道Ch上未确认交付标签5,6,7和8 ，当确认帧在delivery_tag设置为8 并且multiple设置为true的情况下到达该通道时，将确认从5到8的
            所有标签。如果多个设置为false，那么交付5,6和7仍然是未确认的。
            */
            try {
                doWork(message);
            } finally {
                log.debug(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        /**
         * 如果autoAck等于false，消费者从工作队列中时候获取消息后，会发送给rabbitMQ一个确认的消息，
         * 即表示它已经收到了消息。
         * 如果消费者死亡（其通道关闭，连接关闭或TCP连接丢失），RabbitMQ将其定义为消息未被完全处理，
         * 并且重新排队，如果有其他消费者同时在线，它会迅速将其重新发送给另一位消费者，这样即使消费者
         * 偶尔死亡，也可以确保没有任何消息丢失。
         * 当autoAck为true时，如果其中的某些消费者进程挂掉了，则这些消费者带着的消息也会丢失。
         */
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
