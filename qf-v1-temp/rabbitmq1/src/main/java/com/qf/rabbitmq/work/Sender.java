package com.qf.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    //给队列定义的名称
    private static final String QUEUE_NAME = "work_queue";

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

        channel.basicQos(1);
        //声明一个队列,
        // 1;队列名称；2.是否持久化；3，名称唯一，不能给其他人用；4。服务器将在不使用时删除；5.
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发送消息
        for(int i = 0;i<100; i++){
            String message = "message:"+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("第"+i+"发送消息成功");
        }
    }
}
