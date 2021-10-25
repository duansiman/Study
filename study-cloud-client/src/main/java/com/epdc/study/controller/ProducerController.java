package com.epdc.study.controller;

import com.epdc.consumer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @GetMapping(value = "/producer/hello")
    public Mono hello(){
        return Mono.just(producerService.callProducerHello());
    }
}
