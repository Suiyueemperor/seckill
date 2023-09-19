package com.xxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.vo.GoodsVo;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-14
 */
public interface IOrderService extends IService<Order> {

    /**
     * 进行秒杀,返回订单
     * @param user
     * @param goodsVo
     * @return
     */
    Order seckill(User user, GoodsVo goodsVo);
}
