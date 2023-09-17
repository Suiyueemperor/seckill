package com.xxxx.seckill.controller;

import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 商品页面跳转 商品 前端控制器
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-12 23:43
 **/
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;//注入user的服务类
    /**
     * 跳转商品列表页
     * @param model
     * @param
     * @return
     */
    @RequestMapping("/toList")
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, @CookieValue("userTicket") String ticket){//从Cookie中获取参数
        //去掉了参数session
        if (StringUtils.isEmpty(ticket)){
            return "login";
        }
//        User user = (User) session.getAttribute(ticket);
        //通过user获取cookie
        User user = userService.getUserByCookie(ticket,request,response);
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        return "goodsList";
    }
}
