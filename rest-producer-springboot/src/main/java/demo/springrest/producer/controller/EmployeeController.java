package demo.springrest.producer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.springrest.producer.model.Employee;

@RestController
public class EmployeeController {
	static List<Employee> employeesList = new ArrayList<Employee>();
	static{
		employeesList.add(new Employee(1,"Michael","Novo","howtodoinjava@gmail.com"));
		employeesList.add(new Employee(2,"David","Johnson","howtodoinjava@gmail.com"));
	}
	
	@RequestMapping("/")
    public List<Employee> getEmployees() 
    {
		
		return employeesList;
    }

}
