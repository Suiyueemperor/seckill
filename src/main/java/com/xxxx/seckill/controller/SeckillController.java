package com.xxxx.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.pojo.SeckillOrder;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IGoodsService;
import com.xxxx.seckill.service.IOrderService;
import com.xxxx.seckill.service.ISeckillOrderService;
import com.xxxx.seckill.vo.GoodsVo;
import com.xxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class SeckillController {

    @Autowired
    private IGoodsService goodsService;//注入获取商品相关信息
    @Autowired
    private ISeckillOrderService seckillOrderService;//注入获取秒杀商品订单信息 秒杀订单有了 需要正式下订单
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, User user, Long goodsId){
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
        return "orderDetail";
    }
}
