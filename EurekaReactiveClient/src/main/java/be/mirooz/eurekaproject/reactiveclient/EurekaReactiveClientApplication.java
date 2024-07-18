package be.mirooz.eurekaproject.reactiveclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EurekaReactiveClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaReactiveClientApplication.class, args);
    }
}