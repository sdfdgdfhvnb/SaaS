package com.itheima.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 连接工具类
 */
public class ConnectionUtil {

    public static Connection getConnectionFactory() throws Exception {

        // 1.创建连接工厂(设置连接相关参数)
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1"); // 连接的ip地址
        factory.setPort(5672);        // 连接的端口号
        factory.setVirtualHost("/itcast"); // 连接的主机名
        factory.setUsername("heima");      // 账号
        factory.setPassword("heima");      // 密码

        // 2.创建连接
        return factory.newConnection();
    }
}
