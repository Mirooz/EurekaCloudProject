package be.mirooz.eurekaproject.reactiveclient.service;

import be.mirooz.eurekaproject.reactiveclient.reactiveClients.ReactiveEurekaClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@ReactiveEurekaClient
@FeignClient("EUREKA-CLIENT-ONE")
public interface ClientOneController {
    @RequestMapping("/greeting")
    Mono<String> greeting();
}
