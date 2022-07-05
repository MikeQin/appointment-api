package com.appointment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.entity.Agent;
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
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> create(@RequestBody Agent agent) {
		
		Agent agentEntity = agentService.create(agent);
		
		return ResponseEntity.ok("agent.id: " + agentEntity.getId());
	}
	
}
