package com.epdc.study.controller;

//import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class HelloController {

//    @Autowired
//    private Tracer tracer;

    @GetMapping(value = "/consumer/hello")
    public Mono hello(){
//        String[] ids = tracer.currentSpan().context().toString().split("/");
        log.debug("hello method, traceId:{},spanId:{}");
        return Mono.just("consumer: hello caller");
    }
}
