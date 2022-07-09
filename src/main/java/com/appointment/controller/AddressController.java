package com.appointment.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.appointment.entity.Address;
import com.appointment.entity.PersistentException;
import com.appointment.model.AddressTO;
import com.appointment.service.AddressService;

@RestController
@RequestMapping(value = "/api/addresses")
public class AddressController {

	@Autowired
	AddressService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> get(@RequestParam(name = "city", defaultValue = "", required = false) String city,
			@RequestParam(name = "state", defaultValue = "", required = false) String state) {
		
		List<Address> addresses = null;
		
		if (!city.isBlank()) {
			addresses = service.getByCity(city);
		}
		else if (!state.isBlank()) {
			addresses = service.getByState(state);
		}
		else {
			addresses = service.getAll();
		}
		
		return ResponseEntity.ok(addresses);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getById(@PathVariable(name = "id", required = true) Long id) {

		Address address = null;
		try {
			address = service.getById(id);
		} catch (PersistentException e) {
			ClientError error 
				= ClientError.builder().error("Invalid ID [" + id + "]").build();
			return ResponseEntity.badRequest().body(error);
		}

		return ResponseEntity.ok(address);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> create(@RequestBody AddressTO addressTO) {
		Address entity = service.create(addressTO);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(entity.getId()).toUri();

		return ResponseEntity.created(location).body(entity);
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(@PathVariable(name = "id", required = true) Long id) {
		
		try {			
			service.delete(id);
			Status status = Status.builder().message("DELETE SUCCESS, ID: " + id).build();
			return ResponseEntity.ok(status);
			
		} catch (PersistentException e) {
			ClientError error 
				= ClientError.builder().error("Invalid ID [" + id + "]").build();
			return ResponseEntity.badRequest().body(error);
		}
	}
}
