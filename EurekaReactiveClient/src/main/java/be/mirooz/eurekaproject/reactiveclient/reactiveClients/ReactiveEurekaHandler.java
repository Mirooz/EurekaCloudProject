package be.mirooz.eurekaproject.reactiveclient.reactiveClients;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Aspect
@Component
public class ReactiveEurekaHandler {

    @Autowired
    private ReactorLoadBalancerExchangeFilterFunction lbFunction;
    @Bean
    public WebClient.Builder webClient() {
        return WebClient.builder().filter(lbFunction);
    }
    @Around("@within(reactiveEurekaClient)")
    public Object handle(ProceedingJoinPoint joinPoint, ReactiveEurekaClient reactiveEurekaClient) throws Throwable {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        FeignClient feignClient = AnnotationUtils.findAnnotation(targetClass, FeignClient.class);

        if (feignClient != null) {
            String clientName = feignClient.value();
            String methodName = joinPoint.getSignature().getName();

            String uri = "http://" + clientName + "/" + methodName;

            return webClient().build().get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class);
        }
        return joinPoint.proceed();
    }
}
