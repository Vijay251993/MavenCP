package com.example.Consumer.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.Consumer.model.Department;
import com.example.Consumer.model.Employee;

@RestController
public class EmployeeController {

	private final RestTemplate restTemplate;
	private final String url ="";

	public EmployeeController(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}

	@GetMapping("/employee")
	public List<Employee> getEmployee() {
		List<Employee> employeeList = null;
		ResponseEntity<Employee[]> response = this.restTemplate.getForEntity("http://localhost:1230/employee",
				Employee[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			Employee[] employees = response.getBody();
			employeeList = Arrays.asList(employees);
		}
		return employeeList;
	}

	@GetMapping("/employee/{id}")
	public Employee getEmployee(@PathVariable("id") int id) {
		ResponseEntity<Employee> response = this.restTemplate.getForEntity("http://localhost:1230/employee/{id}",
				Employee.class, id);
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}
		return null;
	}

	@GetMapping("/employee/{id}/department")
	public Department getDepartmentByEmployeeId(@PathVariable("id") int id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.ALL));
		HttpEntity request = new HttpEntity(headers);
		ResponseEntity<Department> responseEntity = this.restTemplate.exchange(
				"http://localhost:1230/employee/{id}/department", HttpMethod.GET, request, Department.class, id);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			return responseEntity.getBody();
		}
		return null;
	}

	@PostMapping("/employee")
	public Employee createEmployee(@RequestBody Employee employee) {
		HttpEntity<Employee> request = new HttpEntity(employee);
		ResponseEntity<Employee> responseEntity = this.restTemplate.postForEntity("http://localhost:1230/employee",
				request, Employee.class);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			return responseEntity.getBody();
		}
		return null;
	}

	@PostMapping("/multi-employee")
	public List<Employee> createEmployees(@RequestBody List<Employee> employeess)
	{
		List<Employee> employeeList = null;
		HttpEntity<List<Employee>> request = new HttpEntity(employeess);
		ResponseEntity<Employee[]> response = this.restTemplate.postForEntity("http://localhost:1230/mult-employee", request, Employee[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			Employee[] employees1 = response.getBody();
			employeeList = Arrays.asList(employees1);
		}
		return employeeList;
	}
}
