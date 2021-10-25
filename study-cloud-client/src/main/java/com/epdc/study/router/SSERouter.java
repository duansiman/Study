package com.epdc.study.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Configuration
public class SSERouter {

    @Bean
    public RouterFunction<ServerResponse> router(){
        return RouterFunctions.route(RequestPredicates.GET("/times"), serverRequest -> {
            return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(Flux.interval(Duration.ofSeconds(1))
                            .map(sec -> new SimpleDateFormat("HH:mm:ss").format(new Date())), String.class);
        });
    }

}
