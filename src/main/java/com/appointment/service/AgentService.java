package com.appointment.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
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
	
	public long count() {
		return repo.count();
	}

	public Agent getById(Long id) {
		
		Agent agentEntity = null;	
		try {
			agentEntity = repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new PersistentException(e.getMessage());
		}	
		return agentEntity;
	}
	
	public Agent getByEmail(String email) {		
		return repo.findByEmail(email);
	}
	
	public List<Agent> getByFirstName(String firstName) {
		return repo.findByFirstName(firstName);
	}
	
	public List<Agent> getByLastName(String lastName) {
		return repo.findByFirstName(lastName);
	}
	
	public Agent create(Agent agent) {
		return create(agent, false);
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

	/**
	 * 
	 * @param id
	 * @param agent, the full copy of the original entity
	 * @return
	 */
	public Agent update(Long id, Agent agent) {

		Agent agentEntity = null;
		try {
			agentEntity = repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new PersistentException(e.getMessage());
		}

		//Agent.copy(agent, agentEntity);
		BeanUtils.copyProperties(agent, agentEntity);
		agentEntity = repo.save(agentEntity);

		return agentEntity;
	}

	public void delete(Long id) {

		try {
			repo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new PersistentException(e.getMessage());
		}
	}

}
