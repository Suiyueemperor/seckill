package com.xxxx.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 秒杀信息 消息对象
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-10-09 00:46
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillMessage {
    private User user;
    private long goodsId;
}
