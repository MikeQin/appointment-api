package com.appointment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.appointment.entity.Agent;
import com.appointment.service.AgentService;
import com.appointment.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DbBootstrap implements CommandLineRunner {
	
	AgentService agentService;
	CustomerService customerService;
	
	public DbBootstrap(AgentService agentService,
			CustomerService customerService) {
		this.agentService = agentService;
		this.customerService = customerService;
	}

	@Override
	public void run(String... args) throws Exception {
		
		if (agentService.getAll().size() > 0) {
			return;
		}
		
		Agent a1 = Agent.builder().firstName("John").lastName("Doe").email("john.doe@gmail.com").build();
		Agent a2 = Agent.builder().firstName("Amy").lastName("Smith").email("amy.smith@gmail.com").build();
		
		a1 = agentService.create(a1, false);
		a2 = agentService.create(a2, false);
		
		log.info(a1.toString());
		log.info(a2.toString());
	}
}
