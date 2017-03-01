package demo.spring3rest.consumer.service;

import java.util.List;

import demo.spring3rest.consumer.form.Employee;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
	
	public ResponseEntity addEmployee(Employee contact);
	public List<Employee> listEmployee();
	public ResponseEntity removeEmployee(Integer id);
}
