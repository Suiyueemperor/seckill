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

    /**
     * 监听队列1
     * @param msg
     */
    @RabbitListener(queues = "queue_direct01")
    public void receive01(Object msg) {
        log.info("QUEUE01接受消息：" + msg);
    }

    /**
     * 监听队列2
     * @param msg
     */
    @RabbitListener(queues = "queue_direct02")
    public void receive02(Object msg) {
        log.info("QUEUE02接受消息：" + msg);
    }

}
