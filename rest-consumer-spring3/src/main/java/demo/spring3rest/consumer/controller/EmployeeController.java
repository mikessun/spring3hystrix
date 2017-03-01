package demo.spring3rest.consumer.controller;

import java.util.Map;

import demo.spring3rest.consumer.form.Employee;
import demo.spring3rest.consumer.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("/index")
	public String listEmployee(Map<String, Object> map) {
		map.put("employee",new Employee());

		map.put("contactList", employeeService.listEmployee());

		return "employee";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addEmployee(@ModelAttribute("employee")
								 Employee contact, BindingResult result) {

		employeeService.addEmployee(contact);

		return "redirect:/index";
	}

	@RequestMapping("/delete/{contactId}")
	public String deleteEmployee(@PathVariable("contactId")
	Integer contactId) {

		employeeService.removeEmployee(contactId);

		return "redirect:/index";
	}
}
