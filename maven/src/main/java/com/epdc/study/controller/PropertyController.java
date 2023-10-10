package com.epdc.study.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;

@RestController
public class PropertyController implements EnvironmentAware {

    private Environment environment;

    @GetMapping(value = "/property")
    public String getProperty(@RequestParam("name") String name) {
        return environment.getProperty(name);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
