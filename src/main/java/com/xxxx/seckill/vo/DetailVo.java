package com.xxxx.seckill.vo;

import com.xxxx.seckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 详情返回对象
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-10-06 16:57
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {
    private User user;// 用户
    private GoodsVo goodsVo;// 商品
    private int secKillStatus;// 秒杀状态
    private int remainSeconds;// 剩余时间
}
