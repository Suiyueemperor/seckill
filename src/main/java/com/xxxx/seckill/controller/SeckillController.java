package com.xxxx.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.pojo.SeckillMessage;
import com.xxxx.seckill.pojo.SeckillOrder;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.rabbitmq.MQSender;
import com.xxxx.seckill.service.IGoodsService;
import com.xxxx.seckill.service.IOrderService;
import com.xxxx.seckill.service.ISeckillOrderService;
import com.xxxx.seckill.utils.JsonUtil;
import com.xxxx.seckill.vo.GoodsVo;
import com.xxxx.seckill.vo.RespBean;
import com.xxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 秒杀页面跳转
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-19 10:33
 **/
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    private IGoodsService goodsService;//注入获取商品相关信息
    @Autowired
    private ISeckillOrderService seckillOrderService;//注入获取秒杀商品订单信息 秒杀订单有了 需要正式下订单
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MQSender mqSender;

    //内存标记,标识是否还有库存,减少redis的访问,false表示有库存
    private Map<Long, Boolean> EmptyStockMap = new HashMap<>();

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     * orderId: 成功; -1:失败,0:排队中
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(User user,Long goodsId){
        if (user == null) {
            return RespBean.error(RespBeanEnum.SEESION_ERROR);
        }
        Long orderId = seckillOrderService.getResult(user,goodsId);
        return RespBean.success(orderId);
    }

    @RequestMapping("/doSeckill2")
    public String doSeckill2(Model model, User user, Long goodsId){
        if (user == null) {
            return "login";
        }
        model.addAttribute("user",user);
        //判断库存
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() < 1) {//库存不足
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "seckillFail";
        }
        //判断是否重复抢购(mybatisPlus写法) 前半部eq是获取该用户的订单信息,后eq判断当前goods是否已购买
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
                .eq("user_id", user.getId()).eq("goods_id", goodsId));
        if (seckillOrder != null) {
            model.addAttribute("errmsg",RespBeanEnum.REPEAT_ERROR.getMessage());
            return "seckillFail";
        }
        Order order = orderService.seckill(user, goodsVo);//进行秒杀,返回订单
        model.addAttribute("order", order);//放入订单
        model.addAttribute("goods", goodsVo);//放入商品
        return "orderDetail.htm";
    }
    @RequestMapping(value = "/doSeckill",method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill(Model model, User user, Long goodsId){
        if (user == null) {
            return RespBean.error(RespBeanEnum.SEESION_ERROR);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //使用redis中的索引判断 重复抢购
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("user:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            model.addAttribute("errmsg",RespBeanEnum.REPEAT_ERROR.getMessage());
            return RespBean.error(RespBeanEnum.REPEAT_ERROR);
        }
        //内存标记,能够减少redis的访问,进行流量削峰
        if (EmptyStockMap.get(goodsId)) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //预减库存
        // decrement调用一次减一,原子性操作
        Long stock = valueOperations.decrement("seckillGoods" + goodsId);
        if (stock<0) {
            EmptyStockMap.put(goodsId,true);
            valueOperations.increment("seckillGoods" + goodsId);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //调用消息队列
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));

        return RespBean.success(0);
        /*
        model.addAttribute("user",user);
        //判断库存
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() < 1) {//库存不足
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //判断是否重复抢购(mybatisPlus写法) 前半部eq是获取该用户的订单信息,后eq判断当前goods是否已购买
//        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
//                .eq("user_id", user.getId()).eq("goods_id", goodsId));

        //使用redis中的索引判断
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("user:" + user.getId() + ":" + goodsVo.getId());

        if (seckillOrder != null) {
            model.addAttribute("errmsg",RespBeanEnum.REPEAT_ERROR.getMessage());
            return RespBean.error(RespBeanEnum.REPEAT_ERROR);
        }
        Order order = orderService.seckill(user, goodsVo);//进行秒杀,返回订单
//        model.addAttribute("order", order);//放入订单
//        model.addAttribute("goods", goodsVo);//放入商品

        return RespBean.success(order);*/
    }

    /**
     * 系统初始化，把商品库存数量加载到Redis
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (list.isEmpty()) {
            return;
        }
        list.forEach(goodsVo -> {
                redisTemplate.opsForValue().set("seckillGoods"+goodsVo.getId(),goodsVo.getStockCount(),1, TimeUnit.DAYS);//stock_count秒杀商品库存
                EmptyStockMap.put(goodsVo.getId(),false);
            }
        );

    }
}
