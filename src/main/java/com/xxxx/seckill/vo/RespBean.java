package com.xxxx.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 公共返回对象
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-14 14:50
 **/

@Data //lombok 自动生成getter setter equals hashcode toString 全参/无参构造函数
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private long code;//状态码
    private String message;//对应的消息
    private Object obj;//返回的方法

    /**
     * 成功返回结果
     * @param obj
     * @return RespBean
     */
    public static RespBean success(Object obj){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), obj);
    }

    /**
     * 失败返回结果 不是500即失败
     * @param respBeanEnum
     * @return RespBean
     */
    public static RespBean error(RespBeanEnum respBeanEnum){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param respBeanEnum
     * @return RespBean
     */
    public static RespBean error(RespBeanEnum respBeanEnum,Object obj){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }
}
