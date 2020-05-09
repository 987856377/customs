package com.gov.customs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","DELETE");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/img/")
                .addResourceLocations("classpath:/js/")
                .addResourceLocations("classpath:/css/")
                .addResourceLocations("classpath:/jquery/")
                .addResourceLocations("classpath:/popper/")
                .addResourceLocations("classpath:/bootstrap/")
                .addResourceLocations("classpath:/bootstrap-table/")
                .addResourceLocations("classpath:/font-awesome-4.7.0/");
    }
}
