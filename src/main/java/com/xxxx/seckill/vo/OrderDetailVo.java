package com.xxxx.seckill.vo;

import com.xxxx.seckill.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 订单详情返回对象
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-10-06 21:02
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {
    private Order order;
    private GoodsVo goodsVo;
}
