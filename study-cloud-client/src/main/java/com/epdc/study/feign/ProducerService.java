package com.epdc.study.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "producer-service")
public interface ProducerService {

    @RequestMapping(value = "/producer/hello", method = RequestMethod.POST)
    String hello();

}
