package be.mirooz.eurekaproject.firstclient.controller;


import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.logging.Logger;

@RestController
public class EurekaController {

    Logger logger = Logger.getLogger("EurekaController");
    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping("/greeting")
    public Mono<String> greeting() throws InterruptedException {
        Timestamp timestamp = Timestamp.from(Instant.now());
        logger.info("Execute client 1");
        Thread.sleep(3000);
        return Mono.just(String.format(
                "Hello from '%s at %s'!", eurekaClient.getApplication(appName).getName(),timestamp));
    }
}