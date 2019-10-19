package com.itheima.simple;

import com.itheima.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消息模型(1) 简单模型: 消费者
 * 特点: 一个生产者,一个消费者
 */
public class Consumer {

    public static void main(String[] args) throws Exception {

        // 1.创建连接工厂
        // 2.创建连接
        Connection connection = ConnectionUtil.getConnectionFactory();

        // 3.创建频道
        Channel channel = connection.createChannel();

        // 4.声明队列
        channel.queueDeclare(Producer.QUEUE_NAME, true, false, false, null);

        // 5.创建消费者(接收消息并且处理消息)
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                    String consumerTag, Envelope envelope,
                    AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("交换机:" + envelope.getExchange());
                System.out.println("路由key:" + envelope.getRoutingKey());
                System.out.println("消息ID:" + envelope.getDeliveryTag());
                System.out.println("消息内容:" + new String(body));
            }
        };

        // 监听消息队列
        channel.basicConsume(Producer.QUEUE_NAME, true, consumer);

        // 注意: 这里不要关闭...
    }
}
