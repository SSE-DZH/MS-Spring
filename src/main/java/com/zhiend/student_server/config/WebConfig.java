package com.zhiend.student_server.config;

import com.zhiend.student_server.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    //拦截器对象
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器对象
        registry.addInterceptor(loginCheckInterceptor).excludePathPatterns("/**");
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/register");
    }

//@Override
//public void addInterceptors(InterceptorRegistry registry) {
//    // 注册自定义拦截器对象
//    registry.addInterceptor(loginCheckInterceptor)
//            .addPathPatterns("/teacher/findByUsername/**"); // 添加要拦截的路径
//}
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*")// 允许的源地址
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
//                .allowedHeaders("*") // 允许的头部信息
//                .exposedHeaders("Authorization") // 允许暴露的响应头
//                .allowCredentials(true); // 允许发送 Cookie
//    }

}
