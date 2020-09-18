//跨域配置

package com.example.demo.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //所有路由均可跨域
            .allowedOrigins("*")               //所有请求ip均可跨域
            .allowedMethods("*")               //所有请求方式均可跨域
            .maxAge(3600)
                .allowCredentials(true)        //是否可以将请求的响应头暴露出来
                .allowedHeaders("*")           //所有请求头均可跨域
                .exposedHeaders("token");      //讲header中自定义添加的token字段暴露出来
    }
}
