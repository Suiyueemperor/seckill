package com.xxxx.seckill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * <p>
 * MVC配置, 对ticket, user做验证
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-17 20:55
 **/
@Configuration //定义为配置
@EnableWebMvc //开启MVC
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private UserArgumentResolver userArgumentResolver;
    /**
     * 自定义参数, 用了一个自定义参数解析器 resolver 来做参数校验
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //resolver 是一个list
        resolvers.add(userArgumentResolver);//添加解析器
    }
}
