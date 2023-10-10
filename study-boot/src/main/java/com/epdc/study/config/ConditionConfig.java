package com.epdc.study.config;

import com.epdc.study.service.HelloService;
import com.epdc.study.service.impl.JackHelloService;
import com.epdc.study.service.impl.RoseHelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
public class ConditionConfig {

    @Bean
    @Profile("dev")
    //@Conditional(EnvPropertiesCondition.class)
    HelloService roseHelloService(){
        return new RoseHelloService();
    }

    @Bean
    @Profile("test")
    //@Conditional(EnvPropertiesCondition.class)
    HelloService jackHelloService(){
        return new JackHelloService();
    }
}
