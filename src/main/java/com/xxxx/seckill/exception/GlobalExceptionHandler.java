package com.xxxx.seckill.exception;

import com.xxxx.seckill.vo.RespBean;
import com.xxxx.seckill.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * <p>
 * 全局异常处理类
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-12 16:01
 **/
@RestControllerAdvice//通过advice和handler这两个组合注解来处理的.<自由度更高一些>
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e){
        if (e instanceof GlobalException){//如果是全局异常
            GlobalException ex = (GlobalException) e;
            return RespBean.error(ex.getRespBeanEnum());//返回异常信息
        } else if(e instanceof BindException){//如果是绑定异常
            BindException ex = (BindException) e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        System.out.println("异常信息" + e);
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
