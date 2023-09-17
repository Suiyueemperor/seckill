package com.xxxx.seckill.vo;

import com.xxxx.seckill.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品返回对象, 包括Goods属性+秒杀商品的属性
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-17 22:33
 **/
@EqualsAndHashCode(callSuper = true) //用于在生成 equals() 和 hashCode() 方法时包含父类的属性
//在使用 (callSuper = true) 时，确保父类实现了 equals() 和 hashCode() 方法，正确比较和哈希父类的属性。
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Goods {

    private BigDecimal seckillPrice;
    private Integer stockCount;//用包装类
    private Date startDate;
    private Date endDate;
}
