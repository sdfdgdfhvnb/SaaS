package com.itheima.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * main方法启动
 */
public class CompanyProvider {
    public static void main(String[] args) throws IOException {

        // 加载配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");

        // 启动
        context.start();

        // 输入后停止
        System.in.read();
    }
}
