package com.qf.rabbitmq.work;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver1 {

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
        //声明一个队列,
        //避免队列的堆积，设置每次最多处理一个消息
        channel.basicQos(2);
        // 1;队列名称；2.是否持久化；3，名称唯一，不能给其他人用；4。服务器将在不使用时删除；5.
        //如果这队列不存在则创建
        //存在不需要创建
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //处理消息
        Consumer consumer = new DefaultConsumer(channel){
            //一旦队列有消息，会回调这个方法来处理
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"utf-8");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者1接收到消息："+message);
                //手工确定消息已经被处理
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        // channel.basicConsume(QUEUE_NAME,true,consumer);
        //将消息的确认模式有主动改为手动
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }


}
