package com.epdc.study.service.impl;

import com.epdc.study.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class RoseHelloService implements HelloService {
    @Override
    public String hello() {
        return "hello rose";
    }
}
