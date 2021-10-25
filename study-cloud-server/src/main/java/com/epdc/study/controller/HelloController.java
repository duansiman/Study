package com.epdc.study.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class HelloController {

    @Value("${server.port}")
    private int serverPort;

    @GetMapping(value = "/producer/hello")
    public String hello(){
        log.debug("producer hello");
        return "producer: hello caller, port:" + serverPort;
    }
}
