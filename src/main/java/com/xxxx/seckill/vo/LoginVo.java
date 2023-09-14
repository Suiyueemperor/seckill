package com.xxxx.seckill.vo;

import com.sun.istack.internal.NotNull;
import lombok.Data;

/**
 * <p>
 * 获取登录参数mobile,passwd
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-14 15:00
 **/
@Data
public class LoginVo {
    @NotNull
    private String mobile;

    @NotNull
    private String password;
}
