package com.atguigu.springboot.config;


import com.atguigu.springboot.component.LoginHandlerInterceptor;
import com.atguigu.springboot.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {



    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        WebMvcConfigurer.super.addViewControllers(registry);
        // 对 "/hello" 的 请求 redirect 到 "/home"
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("list1");
        registry.addViewController("/empee.html").setViewName("emp/list2");


    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/index.html","/","/user/login","/asserts/**","/style/**","/webjars/bootstrap/**");
    }


    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

}
