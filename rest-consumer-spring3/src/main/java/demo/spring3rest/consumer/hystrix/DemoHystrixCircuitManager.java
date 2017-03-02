package demo.spring3rest.consumer.hystrix;

import com.developmentsprint.spring.breaker.CircuitManager;
import com.developmentsprint.spring.breaker.hystrix.HystrixCircuitManager;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

public class DemoHystrixCircuitManager  {
    @Autowired
    HystrixCircuitManager hystrixCircuitManager;

    public Configuration getConfiguration() {
        return hystrixCircuitManager.getConfiguration();
    }

    public void setConfiguration(Configuration configuration) {
        hystrixCircuitManager.setConfiguration( configuration);
    }

    public Properties getProperties() {
        return hystrixCircuitManager.getProperties();
    }

    public void setProperties(Properties properties) {
        hystrixCircuitManager.setProperties( properties);
    }

    public Object execute(final CircuitManager.Invoker invoker) {
        return hystrixCircuitManager.execute(invoker);
    }

}