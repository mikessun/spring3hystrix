package demo.spring3rest.consumer.service;

import java.util.List;

import demo.spring3rest.consumer.form.Employee;

public interface EmployeeService {
	
	public void addEmployee(Employee contact);
	public List<Employee> listEmployee();
	public void removeEmployee(Integer id);
}
