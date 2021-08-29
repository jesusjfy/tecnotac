package com.tecnotac.ecomerce;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class imgConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/productimgs/**").addResourceLocations("file:productimgs/");
    	//registry.addResourceHandler("/resources/productImages/**").addResourceLocations("/resources/productImages/");
    }
}