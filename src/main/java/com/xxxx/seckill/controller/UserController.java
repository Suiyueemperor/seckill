package com.xxxx.seckill.controller;


import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.rabbitmq.MQSender;
import com.xxxx.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-03
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MQSender mqSender;
    /**
     * 用户信息(测试接口)
     * @param user
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user) {
        return RespBean.success(user);
    }

    /**
     * 测试发送RabbitMQ消息
     */
    @RequestMapping("/mq")
    @ResponseBody
    public void mq(){
        mqSender.send("hello");
    }
    @RequestMapping("/mq/direct01")
    @ResponseBody
    public void mq01(){
        mqSender.send01("hello,red");
    }
    @RequestMapping("/mq/direct02")
    @ResponseBody
    public void mq02(){
        mqSender.send02("hello,green");
    }
    @RequestMapping("/mq/topic01")
    @ResponseBody
    public void mq03(){
        mqSender.send03("hello,topic01");
    }
    @RequestMapping("/mq/topic02")
    @ResponseBody
    public void mq04(){
        mqSender.send04("hello,topic02");
    }


}
