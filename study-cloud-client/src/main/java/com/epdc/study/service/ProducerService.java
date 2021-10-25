package com.epdc.study.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProducerService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "callProducerHelloFallback")
    public String callProducerHello(){
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://producer-service/producer/hello", null, String.class);
        return responseEntity.getBody();
    }

    public String callProducerHelloFallback(){
        return "consumer default producer hello";
    }

}
