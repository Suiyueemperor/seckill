package com.xxxx.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息消费者
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-10-07 13:49
 **/
@Service
@Slf4j
public class MQReceiver {

    @RabbitListener(queues = "queue")//监听
    public void receive(Object msg){
        log.info("接受消息: "+msg);
    }
}
