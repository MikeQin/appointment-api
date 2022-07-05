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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.appointment.entity.Agent;
import com.appointment.entity.PersistentException;
import com.appointment.service.AgentService;

@RestController
@RequestMapping(value = "/api/agents")
public class AgentController {

	@Autowired
	AgentService agentService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Agent>> getAll() {
		List<Agent> agents = agentService.getAll();

		return ResponseEntity.ok(agents);
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Agent> getById(@PathVariable(value="id") Long id) {
		Agent agentEntity = null;
		try {
			agentEntity = agentService.getById(id);
		} catch (PersistentException e) {
			return ResponseEntity.notFound().build();
		}			

		return ResponseEntity.ok(agentEntity);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Agent> create(@RequestBody Agent agent) {

		Agent agentEntity = agentService.create(agent);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(agentEntity.getId()).toUri();

		return ResponseEntity.created(location).body(agentEntity);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Agent> update(@PathVariable(value = "id") Long id, @RequestBody Agent agent) {

		Agent agentEntity = null;
		
		try {
			agentEntity = agentService.update(id, agent);			
		} catch (PersistentException e) {
			return ResponseEntity.notFound().build();
		}			

		return ResponseEntity.ok(agentEntity);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, String>> delete(@PathVariable(value = "id") Long id) {
		Map<Long, String> result = new HashMap<>();
		
		try {			
			agentService.delete(id);
			result.put(id, "OK");
		} catch (PersistentException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(result);
	}

}
