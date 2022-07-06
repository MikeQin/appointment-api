package com.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.appointment.entity.Agent;
import com.appointment.entity.PersistentException;
import com.appointment.repository.AgentRepository;

@Service
public class AgentService {

	private AgentRepository repo;

	public AgentService(AgentRepository repo) {
		this.repo = repo;
	}

	public List<Agent> getAll() {
		List<Agent> agents = repo.findAll();

		return agents;
	}

	public Agent getById(Long id) {
		
		Agent agentEntity = null;
		
		try {
			agentEntity = repo.findById(id).get();
		} catch (IllegalArgumentException e) {
			throw new PersistentException(e.getMessage());
		}
		
		return agentEntity;
	}

	public Agent create(Agent agent, boolean flush) {

		Agent agentEntity = repo.save(agent);
		
		if (flush) {
			agentEntity = repo.saveAndFlush(agent);
		} else {
			agentEntity = repo.save(agent);
		}
		
		return agentEntity;
	}

	public Agent update(Long id, Agent agent) {

		Agent agentEntity = null;

		try {
			agentEntity = repo.findById(id).get();
		} catch (IllegalArgumentException e) {
			throw new PersistentException(e.getMessage());
		}

		Agent.copy(agent, agentEntity);
		agentEntity = repo.save(agentEntity);

		return agentEntity;
	}

	public void delete(Long id) {

		try {
			repo.deleteById(id);
		} catch (IllegalArgumentException e) {
			throw new PersistentException(e.getMessage());
		}
	}

}
