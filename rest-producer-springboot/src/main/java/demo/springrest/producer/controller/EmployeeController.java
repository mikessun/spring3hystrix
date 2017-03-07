package demo.springrest.producer.controller;

import java.util.ArrayList;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.springrest.producer.model.Employee;

import javax.ws.rs.PathParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController

public class EmployeeController { 
    static Map<Integer, Employee> employeesMap = new HashMap<Integer, Employee>();

    static {
        employeesMap.put(1, new Employee(1, "Michael", "Novo", "howtodoinjava@gmail.com"));
        employeesMap.put(2, new Employee(2, "David", "Johnson", "howtodoinjava@gmail.com"));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Employee> getEmployees() {
        int random = ((int) Math.random() * 100);
		if (random < 0) {
			random = -1 * random;
		}
		random = random % 2;
		log.info("requested: {}, random={}", "getEmployees", random);
        try {
            Thread.sleep(2000*random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList(employeesMap.values());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    public ResponseEntity deleteEmployees(@PathVariable(value = "id") Integer id) {
		log.info("requested: {}", "deleteEmployees", id);
		
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Employee remove=employeesMap.remove(id);
        System.out.println("removed=" + remove );
        if (remove == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public Employee addEmployees(@RequestBody Employee input) { 
		log.info("requested: {}, {}", "addEmployees", input.getId());
	
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        employeesMap.put(input.getId(), input);
        return input;
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/update")
    public ResponseEntity updateEmployees(@RequestBody Employee input) {
		log.info("requested: {}, {}", "updateEmployees", input.getId());
		
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		employeesMap.put(input.getId(), input);
        return ResponseEntity.ok().build();
    }

}
