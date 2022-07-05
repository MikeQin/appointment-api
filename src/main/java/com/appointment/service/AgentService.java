package com.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.appointment.entity.Agent;
import com.appointment.repository.AgentRepository;

@Service
public class AgentService {
	
	private AgentRepository repo;
	
	public AgentService(AgentRepository repo) {
		this.repo = repo;
	}
	
	public Agent create(Agent agent) {
		
		Agent agentEntity = repo.save(agent);
		
		return agentEntity;
	}
	
	public List<Agent> getAll() {
		List<Agent> agents = repo.findAll();
		
		return agents;
	}

}
