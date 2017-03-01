package demo.spring3rest.consumer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import demo.spring3rest.consumer.form.Employee;
import org.springframework.util.CollectionUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static Map<Integer, Employee> employeeList = new HashMap<Integer, Employee>();

    static {
        Employee employee=new Employee();
        employee.setId(1);
        employee.setEmail("wooden@email.com");
        employee.setFirstName("Michael");
        employee.setLastName("Wooden");
        employeeList.put(employee.getId(),employee);
    }
    public void addEmployee(Employee contact) {
        if (contact.getId() == null) {
            contact.setId(employeeList.size()+1);
        }
        employeeList.put(contact.getId(), contact);
    }

    public List<Employee> listEmployee() {
        return new ArrayList<Employee>(employeeList.values());
    }

    public void removeEmployee(Integer id) {
        employeeList.remove(id);
    }
}
