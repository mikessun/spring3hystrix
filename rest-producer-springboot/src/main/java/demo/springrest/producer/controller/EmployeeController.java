package demo.springrest.producer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.springrest.producer.model.Employee;

import javax.ws.rs.PathParam;

@RestController

public class EmployeeController {
    static List<Employee> employeesList = new ArrayList<Employee>();

    static {
        employeesList.add(new Employee(1, "Michael", "Novo", "howtodoinjava@gmail.com"));
        employeesList.add(new Employee(2, "David", "Johnson", "howtodoinjava@gmail.com"));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Employee> getEmployees() {
        return employeesList;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    public ResponseEntity deleteEmployees(@PathVariable(value = "id") Integer id) {
        boolean removed = employeesList.removeIf(p -> p.getId().intValue() == id.intValue());
        System.out.println("removed=" + removed);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/add")
    public ResponseEntity addEmployees(@RequestBody Employee input) {
        employeesList.add(input);
        return ResponseEntity.ok().build();
    }

}
