package be.mirooz.eurekaproject.secondclient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
@FeignClient(name = "EUREKA-CLIENT-ONE")
public interface ClientOneController {
    @RequestMapping("/greeting")
    String greeting();
}
