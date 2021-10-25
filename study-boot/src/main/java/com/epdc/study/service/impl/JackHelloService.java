package com.epdc.study.service.impl;

import com.epdc.study.service.HelloService;
import org.springframework.stereotype.Service;


public class JackHelloService implements HelloService {
    @Override
    public String hello() {
        return "hello jack";
    }
}
