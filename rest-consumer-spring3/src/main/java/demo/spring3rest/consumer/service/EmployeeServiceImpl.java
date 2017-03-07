package demo.spring3rest.consumer.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.developmentsprint.spring.breaker.annotations.CircuitBreaker;
import com.developmentsprint.spring.breaker.annotations.CircuitProperty;
import demo.spring3rest.consumer.circuitbreaker.BrokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;

import demo.spring3rest.consumer.form.Employee;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private static Map<Integer, Employee> employeeList = new HashMap<Integer, Employee>();

    static {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setEmail("wooden@email.com");
        employee.setFirstName("Michael");
        employee.setLastName("Wooden");
        employeeList.put(employee.getId(), employee);
    }

    @Value("${producer.url}")
    private String url;

    @Value("${action.add}")
    private String addAction;
    @Value("${action.delete}")
    private String deleteAction;
    @Value("${action.get}")
    private String getAction;

    private int idValue;

    private RestTemplate template;

    public ResponseEntity addEmployee(Employee contact) {
        if (contact.getId() == null) {
            contact.setId(++idValue);
        }
        Employee employee = template.postForObject(url + addAction, contact, Employee.class);
        if (employee == null) {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @CircuitBreaker(
            circuitManager = "circuitManager",
            properties = {
                    @CircuitProperty(key = "execution.isolation.strategy", value = "THREAD"),
                    @CircuitProperty(key = "circuitBreaker.requestVolumeThreshold", value = "1"),

                    @CircuitProperty(key = "fallbackClass", value = "demo.spring3rest.consumer.hystrix" +
                            ".FallbackFailsFast"),
                    @CircuitProperty(key = "metrics.rollingStats.timeInMilliseconds", value = "60000"),
                    @CircuitProperty(key = "circuitBreaker.requestVolumeThreshold", value = "10"),

                    @CircuitProperty(key = "circuitBreaker.errorThresholdPercentage", value = "20")
            })
    public List<Employee> listEmployee() {
        log.info("called: {}", "listEmployee");
/*        ResponseEntity<List<Employee>> response =
                new RestTemplate().exchange(url + getAction,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                        });
        List<Employee> employees = response.getBody();*/
        Employee[] employees = template.getForObject(url + getAction, Employee[].class);
        idValue = employees.length;
        return Arrays.asList(employees);
    }

    public ResponseEntity removeEmployee(Integer id) {
        log.info("called: {}", "listEmployee");
        template.delete(url + deleteAction, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    private String defaultResponse() {
        log.info("called: {}", "listEmployee");
        String message = "remote call failed";
        log.error(message);
        throw new RuntimeException(message);
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(1000);
        factory.setConnectTimeout(1000);
        return factory;
    }

    @PostConstruct
    public void postConstruct (){
        template = new RestTemplate(clientHttpRequestFactory());

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        template.getMessageConverters().add(mappingJackson2HttpMessageConverter);

    }
}
