package com.xxxx.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息发送者
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-10-07 13:48
 **/
@Service
@Slf4j
public class MQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发送秒杀信息
    public void sendSeckillMessage(String msg){
        log.info("发送消息: "+msg);
        rabbitTemplate.convertAndSend("seckillExchange","seckill.message",msg);
    }
//    public void send(Object msg){
//        log.info("发送消息: "+msg);
////        rabbitTemplate.convertAndSend("queue",msg);//使用的默认直连
//        rabbitTemplate.convertAndSend("fanoutExchange","",msg);//fanout
//    }
//
//    public void send01(Object msg){
//        log.info("发送red消息: "+msg);
//        rabbitTemplate.convertAndSend("directExchange","queue.red",msg);
//    }
//    public void send02(Object msg){
//        log.info("发送green消息: "+msg);
//        rabbitTemplate.convertAndSend("directExchange","queue.green",msg);
//    }
//    public void send03(Object msg){
//        log.info("发送(QUEUE01)消息: "+msg);
//        rabbitTemplate.convertAndSend("topicExchange","queue.red.message",msg);//满足路由1
//    }
//    public void send04(Object msg){
//        log.info("发送(QUEUE01,QUEUE02)消息: "+msg);
//        rabbitTemplate.convertAndSend("topicExchange","msg.queue.green",msg);//满足两个路由
//    }
//    public void send05(String msg){
//        log.info("发送(QUEUE01,QUEUE02)消息: "+msg);
//        MessageProperties properties = new MessageProperties();
//        properties.setHeader("color","red");
//        properties.setHeader("speed","fast");
//        Message message = new Message(msg.getBytes(), properties);
//        rabbitTemplate.convertAndSend("headersExchange","",message);
//    }
//    public void send06(String msg){
//        log.info("发送(QUEUE01)消息: "+msg);
//        MessageProperties properties = new MessageProperties();
//        properties.setHeader("color","red");
//        properties.setHeader("speed","normal");
//        Message message = new Message(msg.getBytes(), properties);
//        rabbitTemplate.convertAndSend("headersExchange","",message);
//    }


}
