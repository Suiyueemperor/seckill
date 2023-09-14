package com.xxxx.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 测试hello, 项目是否搭建成功
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-14 14:09
 **/
@Controller //有页面跳转就有controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name", "XXXX");//往model中加入了属性 值
        return "hello";//跳转到hell.html
    }
}
