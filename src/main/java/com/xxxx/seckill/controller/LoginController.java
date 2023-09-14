package com.xxxx.seckill.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 跳转登录页面
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-14 14:42
 **/
@Controller //RestController 会都加入返回为ResponseBody,相当于controller+response
@RequestMapping("/login")
@Slf4j //lombok annotation //输出日志,不用自己定义log类来打印日志 log.inform;log.warring;log.error
public class LoginController {

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
}
