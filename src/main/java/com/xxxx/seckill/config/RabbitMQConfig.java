package com.xxxx.seckill.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>
 * rabbitmq配置类
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-10-07 13:42
 **/
@Configuration
public class RabbitMQConfig {

    private static final String QUEUE01 = "queue_fanout01";
    private static final String QUEUE02 = "queue_fanout02";
    private static final String EXCHANGE = "fanoutExchange";

    @Bean
    public Queue queue(){
        //后面表示队列是否持久化,消息+队列持久化.单消息持久化可能失败
        return new Queue("queue", true);
    }

    @Bean
    public Queue queue01(){
        return new Queue(QUEUE01);
    }

    @Bean
    public Queue queue02(){
        return new Queue(QUEUE02);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE);//交换机
    }

    @Bean
    public Binding binding01(){
        //队列2绑定交换机
        return BindingBuilder.bind(queue01()).to(fanoutExchange());
    }

    @Bean
    public Binding binding02(){
        //队列1绑定交换机
        return BindingBuilder.bind(queue02()).to(fanoutExchange());
    }
}
