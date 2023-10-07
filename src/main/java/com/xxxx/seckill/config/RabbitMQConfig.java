package com.xxxx.seckill.config;

import org.springframework.amqp.core.Queue;
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

    @Bean
    public Queue queue(){
        //后面表示队列是否持久化,消息+队列持久化.单消息持久化可能失败
        return new Queue("queue", true);
    }
}
