package demo.spring3rest.consumer.hystrix;


import com.developmentsprint.spring.breaker.hystrix.fallback.HystrixFallback;
import com.netflix.hystrix.HystrixCommandGroupKey;
import demo.spring3rest.consumer.form.Employee;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FallbackFailsFast implements HystrixFallback<Employee> {

    private Employee employee;

    private void init() {
        employee = new Employee();
    }

    public FallbackFailsFast() {
    }

    @Override
    public Employee fallback() {
        log.error("error: {}", "error!");
        return employee;
    }
}
