package com.epdc.study.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestTask {

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void test(){
        log.debug("test task");
    }

}
