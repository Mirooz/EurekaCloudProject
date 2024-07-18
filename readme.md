# Eureka Reactive Client Project

This project demonstrates how to use Spring Cloud Eureka with reactive programming and Feign clients. The project includes an example of dynamically adding Feign clients to interfaces annotated with a custom `@ReactiveEurekaClient` annotation.

## Project Structure

### Packages

- `be.mirooz.eurekaproject.reactiveclient.aspect`: Contains the custom `@ReactiveEurekaClient` annotation and the aspect handler.
- `be.mirooz.eurekaproject.reactiveclient.controller`: Contains the main controller that handles HTTP requests.
- `be.mirooz.eurekaproject.reactiveclient.proxy`: Contains the factory class for creating dynamic Feign client proxies.
- `be.mirooz.eurekaproject.reactiveclient.service`: Contains the service interface annotated with `@ReactiveEurekaClient`.

### Main Components

1. **`@ReactiveEurekaClient` Annotation**:
    - Custom annotation used to mark interfaces for dynamic Feign client addition.
    - Defined in `be.mirooz.eurekaproject.reactiveclient.aspect.ReactiveEurekaClient`.

2. **Aspect Handler**:
    - Intercepts method calls on interfaces annotated with `@ReactiveEurekaClient`.
    - Extracts the client name from the `@ReactiveEurekaClient` annotation and uses `WebClient` to make HTTP requests.
    - Defined in `be.mirooz.eurekaproject.reactiveclient.aspect.ReactiveEurekaClientHandler`.

3. **Feign Client Proxy Factory**:
    - Dynamically adds Feign client behavior to interfaces annotated with `@ReactiveEurekaClient`.
    - Uses Spring's `ApplicationContext` to get the Feign client bean.
    - Defined in `be.mirooz.eurekaproject.reactiveclient.proxy.FeignClientProxyFactory`.

4. **Service Interface**:
    - Example interface annotated with `@ReactiveEurekaClient`.
    - Contains a method `greeting` that is dynamically intercepted.
    - Defined in `be.mirooz.eurekaproject.reactiveclient.service.ClientOneController`.

5. **Controller**:
    - Main controller that handles HTTP requests and calls the service method.
    - Demonstrates how to call the service method 5 times with a half-second delay between each call.
    - Defined in `be.mirooz.eurekaproject.reactiveclient.controller.EurekaClientReactiveController`.

## How It Works

### Annotation and Aspect

- The `@ReactiveEurekaClient` annotation is used to mark interfaces that should have Feign client behavior added dynamically.
- The `ReactiveEurekaClientHandler` aspect intercepts method calls on these interfaces and uses `WebClient` to make HTTP requests to the appropriate Eureka client.

### Dynamic Feign Client Addition

- The `FeignClientProxyFactory` dynamically creates a proxy for interfaces annotated with `@ReactiveEurekaClient`.
- The proxy adds Feign client behavior by delegating method calls to the appropriate Feign client bean.

### Controller Logic

- The `EurekaClientReactiveController` demonstrates how to use the dynamic Feign client.
- It calls the `greeting` method of the `ClientOneController` 5 times with a half-second delay between each call.

## Example Code

### `@ReactiveEurekaClient` Annotation

```java
@ReactiveEurekaClient
@FeignClient("EUREKA-CLIENT-ONE")
public interface ClientOneController {
    @RequestMapping("/greeting")
    Mono<String> greeting();
} 
```

The client can asynchronously call FeignClient through the interface within the controller or service, ensuring a reactive and non-blocking communication flow.
```java
    @RequestMapping("/call-reactive")
    public Flux<String> callClientOne() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofMillis(500))
                .flatMap(i -> clientOneController.greeting())
                .doOnNext(result -> logger.info("Received result: " + result));
    }

```