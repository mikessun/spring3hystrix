package demo.spring3rest.consumer.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@ToString
public class Employee {
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
}
