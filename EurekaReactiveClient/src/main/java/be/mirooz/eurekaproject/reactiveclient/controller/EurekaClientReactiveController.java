package be.mirooz.eurekaproject.reactiveclient.controller;


import be.mirooz.eurekaproject.reactiveclient.service.ClientOneController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RestController
public class EurekaClientReactiveController {
    Logger logger = Logger.getLogger("EurekaClient2Controller");
    @Autowired
    private ReactorLoadBalancerExchangeFilterFunction lbFunction;
    @Bean
    public WebClient.Builder webClient() {
        return WebClient.builder().filter(lbFunction);
    }
    @Autowired
    private ClientOneController clientOneController;

    @RequestMapping("/call-reactive")
    public Mono<String> callClientOne() {
        return webClient().build().get()
                .uri("http://EUREKA-CLIENT-ONE/greeting")
                .retrieve()
                .bodyToMono(String.class);
    }
}