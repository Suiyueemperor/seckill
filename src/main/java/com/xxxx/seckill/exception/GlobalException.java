package com.xxxx.seckill.exception;

import com.xxxx.seckill.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 全局异常
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-12 15:50
 **/
@Data //自己生成 getter setter toString hashcode等函数
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException{//继承运行时异常

    private RespBeanEnum respBeanEnum;//有公共返回对象枚举

}
