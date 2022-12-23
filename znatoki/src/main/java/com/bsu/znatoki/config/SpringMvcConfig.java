package com.bsu.znatoki.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@ComponentScan("com.leverx.blog")
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

}
