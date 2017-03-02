package demo.spring3rest.consumer.circuitbreaker;

import com.developmentsprint.spring.breaker.annotations.CircuitBreaker;
import com.developmentsprint.spring.breaker.annotations.CircuitProperty;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface BrokenService {
        String name() default "";
        CircuitBreaker circuitBreaker() default @CircuitBreaker(
                circuitManager = "circuitManager",
                properties = {
                @CircuitProperty(key = "execution.isolation.strategy", value = "SEMAPHORE"),
                @CircuitProperty(key = "execution.isolation.semaphore.maxConcurrentRequests", value = "10"),
                @CircuitProperty(key = "fallbackMethod", value = "defaultResponse"),
                @CircuitProperty(key = "circuitBreaker.errorThresholdPercentage", value = "1")
        });
}
