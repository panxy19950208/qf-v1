package com.qf.rabbitmq.simple;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {

    //给队列定义的名称
    private static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //服务器地址
        connectionFactory.setHost("192.168.171.134");
        //rabbitmq
        connectionFactory.setVirtualHost("/java1807");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("panxy");
        connectionFactory.setPassword("pan19950208");

        Connection connection = connectionFactory.newConnection();
        //基于连接对象创建channel
        Channel channel = connection.createChannel();
        //声明一个队列,
        // 1;队列名称；2.是否持久化；3，名称唯一，不能给其他人用；4。服务器将在不使用时删除；5.
        //如果这队列不存在则创建
        //存在不需要创建
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //处理消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        //监听队列
        //b:自动确认
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }


}
