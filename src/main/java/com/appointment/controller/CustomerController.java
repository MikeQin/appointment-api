package com.appointment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.entity.Customer;
import com.appointment.service.CustomerService;

@RestController
@RequestMapping(value = "/api/customers")
public class CustomerController {
	
	@Autowired
	CustomerService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> getAll() {
		
		List<Customer> customers = service.getAll();
		return ResponseEntity.ok(customers);
	}

}
