package be.mirooz.eurekaproject.secondclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EurekaSecondClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaSecondClientApplication.class, args);
    }
}