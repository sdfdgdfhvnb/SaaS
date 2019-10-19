package com.itheima.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息模型(2) 工作队列: 生产者,发送多个消息
 * 特点: 一个生产者,多个消费者(消费者之间存在竞争)
 */
public class Producer {

    // 消息发送的目的地: simple_queue这个队列
    static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {

        // 1.创建连接工厂(设置连接相关参数)
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.88.117");
        factory.setPort(5672);
        factory.setVirtualHost("/itcast");
        factory.setUsername("heima");
        factory.setPassword("heima");

        // 2.创建连接
        Connection connection = factory.newConnection();

        // 3.创建频道
        Channel channel = connection.createChannel();

        // 4.声明队列:
        // 参数1: 队列名称
        // 参数2: 是否持久化队列.如果持久化队列,重启服务队列依然存在
        // 参数3: 是否独占本次连接
        // 参数4: 是否自动删除
        // 参数5: 队列其他参数
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 5.发送消息
        for (int i = 0; i < 10; i++) {

            String msg = "Hello,Rabbit!" + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }

        // 关闭资源
        channel.close();
        connection.close();
    }
}
