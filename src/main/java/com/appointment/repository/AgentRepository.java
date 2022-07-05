package com.appointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.appointment.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {

	Agent findByEmail(@Param("email") String email);
	List<Agent> findByFirstName(@Param("firstName") String name);
	List<Agent> findByLastName(@Param("lastName") String name);
}
