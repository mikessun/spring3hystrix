package demo.spring3rest.consumer.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;

import demo.spring3rest.consumer.form.Employee;
import org.springframework.web.client.RestTemplate;

@Service
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

    public ResponseEntity addEmployee(Employee contact) {
        if (contact.getId() == null) {
            contact.setId(++idValue);
        }
        Employee employee = new RestTemplate().postForObject(url + addAction, contact, Employee.class);
        if (employee == null) {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public List<Employee> listEmployee() {

/*        ResponseEntity<List<Employee>> response =
                new RestTemplate().exchange(url + getAction,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                        });
        List<Employee> employees = response.getBody();*/
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        Employee[] employees = restTemplate.getForObject(url + getAction, Employee[].class);
        idValue = employees.length;
        return Arrays.asList(employees);
    }

    public ResponseEntity removeEmployee(Integer id) {
        new RestTemplate().delete(url + deleteAction, id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
