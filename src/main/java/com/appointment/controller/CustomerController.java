package com.appointment.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.appointment.entity.Customer;
import com.appointment.entity.PersistentException;
import com.appointment.service.CustomerService;

@RestController
@RequestMapping(value = "/api/customers")
public class CustomerController {
	
	@Autowired
	CustomerService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAll(
			@RequestParam(name = "email", defaultValue = "", required = false) String email,
			@RequestParam(name = "firstName", defaultValue = "", required = false) String firstName,
			@RequestParam(name = "lastName", defaultValue = "", required = false) String lastName) {
		
		List<Customer> customers = service.getAll();
		
		if (!email.isBlank()) {
			Customer customer = service.getByEmail(email);
			return ResponseEntity.ok(customer);
		}
		else if (!firstName.isBlank()) {
			customers = service.getByFirstName(firstName);
		}
		else if (!lastName.isBlank()) {
			customers = service.getByLastName(lastName);
		}
		else {
			customers = service.getAll();
		}

		return ResponseEntity.ok(customers);
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getById(
			@PathVariable(name = "id", required = true) Long id) {
		
		Customer customer = null;
		try {
			customer = service.getById(id);
		} catch (PersistentException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(customer);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> create(@RequestBody Customer customer) {
		
		Customer entity = service.create(customer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(entity.getId()).toUri();

		return ResponseEntity.created(location).body(entity);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> update(
			@PathVariable(name = "id", required = true) Long id, 
			@RequestBody Customer customer) {

		Customer entity = null;
		
		try {
			entity = service.update(id, customer);			
		} catch (PersistentException e) {
			return ResponseEntity.notFound().build();
		}			

		return ResponseEntity.ok(entity);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, String>> delete(
			@PathVariable(name = "id", required = true) Long id) {
		
		Map<Long, String> result = new HashMap<>();
		
		try {			
			service.delete(id);
			result.put(id, "OK");
		} catch (PersistentException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(result);
	}

}
