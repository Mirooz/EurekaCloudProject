package be.mirooz.eurekaproject.reactiveclient.reactiveClients;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;

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

        try {
            String clientName = feignClient.value();
            String methodName = joinPoint.getSignature().getName();

            String uri = "http://" + clientName + "/" + methodName;
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Type returnType = methodSignature.getReturnType();
            if (returnType.getTypeName().startsWith("reactor.core.publisher.Mono")) {
                ResolvableType resolvableType = ResolvableType.forMethodReturnType(methodSignature.getMethod());
                Class<?> genericType = resolvableType.getGeneric(0).resolve();
                return webClient().build().get()
                        .uri(uri)
                        .retrieve()
                        .bodyToMono(genericType);

            } else if (returnType.getTypeName().startsWith("reactor.core.publisher.Flux")) {
                ResolvableType resolvableType = ResolvableType.forMethodReturnType(methodSignature.getMethod());
                Class<?> genericType = resolvableType.getGeneric(0).resolve();

                return webClient().build().get()
                        .uri(uri)
                        .retrieve()
                        .bodyToFlux(genericType);

            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return joinPoint.proceed();
    }
}
