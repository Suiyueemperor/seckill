package com.xxxx.seckill.rabbitmq;

import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.pojo.SeckillMessage;
import com.xxxx.seckill.pojo.SeckillOrder;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IGoodsService;
import com.xxxx.seckill.service.IOrderService;
import com.xxxx.seckill.utils.JsonUtil;
import com.xxxx.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;
    /**
     * 下单操作
     * @param msg
     */
    @RabbitListener(queues = "seckillQueue")
    public void receive(String msg){
        log.info("QUEUE接受消息: "+msg);
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(msg, SeckillMessage.class);
        long goodsId = seckillMessage.getGoodsId();
        User user = seckillMessage.getUser();
        //判断库存
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount()<1) {
            return;
        }
        //判断重复抢购(mybatis-Plus)
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("user:" + user.getId() + ":" + goodsVo.getId());
        if (seckillOrder != null) {
            return;
        }
        //下单操作
        Order order = orderService.seckill(user, goodsVo);
    }

//    @RabbitListener(queues = "queue")//监听
//    public void receive(Object msg){
//        log.info("接受消息: "+msg);
//    }
//
//    /**
//     * 监听队列1
//     * @param msg
//     */
//    @RabbitListener(queues = "queue_direct01")
//    public void receive01(Object msg) {
//        log.info("QUEUE01接受消息：" + msg);
//    }
//
//    /**
//     * 监听队列2
//     * @param msg
//     */
//    @RabbitListener(queues = "queue_direct02")
//    public void receive02(Object msg) {
//        log.info("QUEUE02接受消息：" + msg);
//    }
//    @RabbitListener(queues = "queue_topic01")
//    public void receive03(Object msg) {
//        log.info("QUEUE01接受消息：" + msg);
//    }
//    @RabbitListener(queues = "queue_topic02")
//    public void receive04(Object msg) {
//        log.info("QUEUE02接受消息：" + msg);
//    }
//    @RabbitListener(queues = "queue_header01")
//    public void receive05(Message msg) {
//        log.info("QUEUE01接受message对象：" + msg);
//        log.info("QUEUE01接受消息：" + new String(msg.getBody()));
//    }
//    @RabbitListener(queues = "queue_header02")
//    public void receive06(Message msg) {
//        log.info("QUEUE02接受message对象：" + msg);
//        log.info("QUEUE02接受消息：" + new String(msg.getBody()));
//    }


}
