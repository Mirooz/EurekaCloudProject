package be.mirooz.eurekaproject.reactiveclient.controller;


import be.mirooz.eurekaproject.reactiveclient.service.ClientOneController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.logging.Logger;

@RestController
public class EurekaClientReactiveController {
    Logger logger = Logger.getLogger("EurekaClient2Controller");

    @Autowired
    private ClientOneController clientOneController;

    @RequestMapping("/call-reactive")
    public Flux<String> callClientOne() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofMillis(500))
                .flatMap(i -> clientOneController.greeting())
                .doOnNext(result -> logger.info("Received result: " + result));
    }
}