package com.itheima.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 发消息测试
 */
public class Send {

    public static void main(String[] args) {

        // 1.创建spring容器
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext-rabbitmq-producer.xml");

        // 2.从容器中获取对象
        RabbitTemplate template = context.getBean(RabbitTemplate.class);

        // 3.发送信息
        Map<String, Object> map = new HashMap<>();
        map.put("email", "2523689574@qq.com");
        template.convertAndSend("msg.email", map);

        context.close();
    }
}
