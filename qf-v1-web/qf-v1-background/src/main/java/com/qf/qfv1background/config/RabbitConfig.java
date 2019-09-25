package com.qf.qfv1background.config;

import com.qf.v1.common.constant.RabbitmqConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    //声明交换机
    public TopicExchange topicExchange(){
        return new TopicExchange(RabbitmqConstant.BACKGROUD_PRODUCT_EXCHANGE);
    }
}
