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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.appointment.entity.Agent;
import com.appointment.entity.PersistentException;
import com.appointment.service.AgentService;

@RestController
@RequestMapping(value = "/api/agents")
public class AgentController {

	@Autowired
	AgentService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> get(@RequestParam(name = "email", defaultValue = "", required = false) String email,
			@RequestParam(name = "firstName", defaultValue = "", required = false) String firstName,
			@RequestParam(name = "lastName", defaultValue = "", required = false) String lastName) {

		List<Agent> agents = null;

		if (!email.isBlank()) {
			Agent agent = service.getByEmail(email);
			return ResponseEntity.ok(agent);
		} else if (!firstName.isBlank()) {
			agents = service.getByFirstName(firstName);
		} else if (!lastName.isBlank()) {
			agents = service.getByLastName(lastName);
		} else {
			agents = service.getAll();
		}

		return ResponseEntity.ok(agents);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getById(@PathVariable(name = "id", required = true) Long id) {

		Agent agent = null;
		try {
			agent = service.getById(id);
		} catch (PersistentException e) {
			ClientError error 
				= ClientError.builder().error("Invalid ID [" + id + "]").build();
			return ResponseEntity.badRequest().body(error);
		}

		return ResponseEntity.ok(agent);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Agent> create(@RequestBody Agent agent) {

		Agent entity = service.create(agent, false);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(entity.getId())
				.toUri();

		return ResponseEntity.created(location).body(entity);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(@PathVariable(name = "id", required = true) Long id,
			@RequestBody Agent agent) {

		Agent entity = null;

		try {
			entity = service.update(id, agent);
		} catch (PersistentException e) {
			ClientError error = ClientError
					.builder().error("Invalid ID [" + id + "]").build();
			return ResponseEntity.badRequest().body(error);
		}

		return ResponseEntity.ok(entity);
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
