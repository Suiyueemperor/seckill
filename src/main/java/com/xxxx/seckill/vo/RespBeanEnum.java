package com.xxxx.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * <p>
 * 公共返回对象枚举类
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-14 14:47
 **/

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //通用
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),
    //登录模块
    LOGIN_ERROR(500210,"用户名或密码错误"),
    MOBILE_ERROR(500211,"手机号码格式不正确"),
    BIND_ERROR(500212, "参数校验异常"),
    ;
    public final Integer code;
    private final String message;
}
