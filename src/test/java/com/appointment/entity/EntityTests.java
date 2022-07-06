package com.appointment.entity;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.appointment.repository.AddressRepository;
import com.appointment.service.AgentService;
import com.appointment.service.AppointmentService;
import com.appointment.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class EntityTests {
	
	@Autowired
	CustomerService customerService;
	@Autowired
	AppointmentService appointmentService;
	@Autowired
	AgentService agentService;
	@Autowired
	AddressRepository addressRepo;
	
	@Test
	void validateOneToManyMapping() {
		
		// Create customer
		Customer cust = Customer.builder().firstName("John").lastName("Smith").email("john.smith@gmail.com")
				.phone("1234567890").build();
		Customer custEntity = customerService.create(cust, false);
		
		// Create address for that customer
		Address address = Address.builder().city("Dallas").state("Texas").customer(custEntity).build();
		address = addressRepo.save(address);
		
		// Verify: Retrieve the customer from Database
		Customer customer = customerService.getById(custEntity.getId());
		// Verify: Get the address from the customer
		List<Address> addr = customer.getAddresses();
		// Assert OneToMany Mapping
		Assertions.assertFalse(addr.isEmpty());
		
		for (Address a : addr) {
			
			// Assert ManyToOne Mapping
			Customer c = a.getCustomer();
			Assertions.assertNotNull(c);
			Assertions.assertEquals("john.smith@gmail.com", c.getEmail());
			Assertions.assertEquals("Dallas", a.getCity());
			
			log.info("***" + c.getEmail());
			log.info("***" + a.getCity());
			
		}
	}

}
