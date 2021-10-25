package com.epdc.study.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class SessionTestController {

    @PostMapping(value = "/session/create")
    public Mono createSession(WebSession webSession){
        webSession.getAttributes().put("key", "value");
        return Mono.just(true);
    }

    @PostMapping(value = "/session/print")
    public Mono printSession(WebSession webSession){
        Map<String, Object> attrs = webSession.getAttributes();
        System.out.println(attrs.toString());
        return Mono.just(true);
    }
}
