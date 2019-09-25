package com.qf.rabbitmq.publish;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver2 {
    //定义交换机名称
    private static final String EXCHANGE_NAME = "fanout_exchange";
    //给队列定义的名称
    private static final String QUEUE_NAME = "fanout_queue_2";

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
        //绑定交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
        //处理消息
        Consumer consumer = new DefaultConsumer(channel){
            //一旦队列有消息，会回调这个方法来处理
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"utf-8");
                System.out.println("消费者2接收到消息："+message);
                //手工确定消息已经被处理
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //channel.basicConsume(QUEUE_NAME,true,consumer);
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }


}
