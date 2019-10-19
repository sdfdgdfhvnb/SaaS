package com.itheima.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消息监听器
 */
@Component
public class EmailMessageListener implements MessageListener {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message message) {
        try {
            JsonNode jsonNode = MAPPER.readTree(message.getBody());
            String email = jsonNode.get("email").asText();
            System.out.println("获取队列中消息：" + email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
