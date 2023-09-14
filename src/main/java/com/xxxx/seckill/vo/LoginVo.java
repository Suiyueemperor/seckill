package com.xxxx.seckill.vo;

import com.sun.istack.internal.NotNull;
import com.xxxx.seckill.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
