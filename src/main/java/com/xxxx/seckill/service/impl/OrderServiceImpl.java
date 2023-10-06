package com.xxxx.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckill.exception.GlobalException;
import com.xxxx.seckill.mapper.SeckillOrderMapper;
import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.mapper.OrderMapper;
import com.xxxx.seckill.pojo.SeckillGoods;
import com.xxxx.seckill.pojo.SeckillOrder;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IGoodsService;
import com.xxxx.seckill.service.IOrderService;
import com.xxxx.seckill.service.ISeckillGoodsService;
import com.xxxx.seckill.service.ISeckillOrderService;
import com.xxxx.seckill.vo.GoodsVo;
import com.xxxx.seckill.vo.OrderDetailVo;
import com.xxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private OrderMapper orderMapper;//将订单写入数据库
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IGoodsService goodsService;

    /**
     * 进行秒杀,返回订单
     * @param user
     * @param goodsVo
     * @return
     */
    @Override
    public Order seckill(User user, GoodsVo goodsVo) {
        //减秒杀商品的库存 通过商品的goodsId获取该商品的秒杀信息seckillGoods
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goodsVo.getId()));
        Integer stockCount = seckillGoods.getStockCount();//获取库存
        seckillGoods.setStockCount(stockCount - 1);//减库存
        seckillGoodsService.updateById(seckillGoods);//更新秒杀库存信息
        //goodsVo.setGoodsStock(goodsVo.getGoodsStock()-1);//原仓库中的也要减,本实验没有考虑
        //生成订单信息
        Order order = new Order();//订单ID不设置 //订单ID默认自动生成
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0L);//收货地址
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);//购买数量
        order.setGoodsPrice(goodsVo.getGoodsPrice());
        order.setOrderChannel((byte) 1);//pc
        order.setStatus((byte) 0);//未付钱
        order.setCreateDate(new Date());//下单时间
        orderMapper.insert(order);
        //生成秒杀订单 订单id和秒杀订单有关联
        SeckillOrder seckillOrder = new SeckillOrder();//秒杀表的id也是自动生成
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());//用订单id即可
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrderService.save(seckillOrder);//秒杀订单与订单关联 只需要将订单写入数据库
        return order;
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @Override
    public OrderDetailVo detail(Long orderId) {
        if (orderId == null) {
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo detail = new OrderDetailVo();
        detail.setOrder(order);
        detail.setGoodsVo(goodsVo);
        return detail;
    }
}
