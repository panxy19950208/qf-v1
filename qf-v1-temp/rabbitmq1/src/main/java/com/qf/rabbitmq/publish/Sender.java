package com.qf.rabbitmq.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    //定义交换机名称
    private static final String EXCHANGE_NAME = "fanout_exchange";

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
        //声明一个交换机,
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //发送消息
        for(int i = 0;i<100; i++){
            String message = "message:"+i;
            //发送到交换机
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("第"+i+"发送消息成功");
        }
    }
}
