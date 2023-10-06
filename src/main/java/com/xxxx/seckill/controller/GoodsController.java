package com.xxxx.seckill.controller;

import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IGoodsService;
import com.xxxx.seckill.service.IUserService;
import com.xxxx.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

//    @Autowired
//    private IUserService userService;//注入user的服务类

    @Autowired
    private IGoodsService goodsService;//注入goods的服务类

    @Autowired
    private RedisTemplate redisTemplate;//注入redis

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;//注入Thymeleaf
    /**
     * 跳转商品列表页
     * @param model
     * @param
     * @return
     */
    @RequestMapping(value = "/toList",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response){//从Cookie中获取参数
        //原参数列表(HttpServletRequest request, HttpServletResponse response, Model model, @CookieValue("userTicket") String ticket)
        //去掉了参数session
        /*if (StringUtils.isEmpty(ticket)){
            return "login";
        }
//        User user = (User) session.getAttribute(ticket);
        //通过user获取cookie
        User user = userService.getUserByCookie(ticket,request,response);
        if (user == null) {
            return "login";
        }*/
        // redis中获取页面, 不为空 直接返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        model.addAttribute("user", user);
        model.addAttribute("goodsList",goodsService.findGoodsVo());
//        return "goodsList";
        // 如果为空,手动渲染Thymeleaf,存入redis
        WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsList",html,60, TimeUnit.SECONDS);//60s过期
        }
        return html;
    }

    /**
     * 跳转商品详情页,goodsId为索引
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/toDetail/{goodsId}",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail(Model model, User user, @PathVariable Long goodsId, HttpServletRequest request, HttpServletResponse response){
        // 直接返回html
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        if (nowDate.before(startDate)) {
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);//转成seconds
        } else if (nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
        }else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("secKillStatus",secKillStatus);
        model.addAttribute("goods",goodsVo);
//        return "goodsDetail";

        // 如果为空,手动渲染Thymeleaf,存入redis
        WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);//处理商品详情
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsDetail:"+goodsId,html,60, TimeUnit.SECONDS);//60s过期
        }
        return html;
    }
}
