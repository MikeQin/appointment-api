package com.appointment;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.appointment.entity.Address;
import com.appointment.entity.Agent;
import com.appointment.entity.Appointment;
import com.appointment.entity.Customer;
import com.appointment.model.AddressTO;
import com.appointment.service.AddressService;
import com.appointment.service.AgentService;
import com.appointment.service.AppointmentService;
import com.appointment.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DbBootstrap implements CommandLineRunner {
	
	AgentService agentService;
	CustomerService customerService;
	AddressService addressService;
	@Autowired // This is another way to inject, not recommended anymore
	AppointmentService appointmentService;
	
	public DbBootstrap(AgentService agentService,
			CustomerService customerService,
			AddressService addressService) {
		this.agentService = agentService;
		this.customerService = customerService;
		this.addressService = addressService;
	}

	@Override
	public void run(String... args) throws Exception {
		
		createAgents();
		createCustomers();
		createAppointment();
	}

	private void createAgents() {
		if (agentService.count() > 0) {
			return;
		}
		
		Agent a1 = Agent.builder().firstName("John").lastName("Doe").email("john.doe@gmail.com").build();
		Agent a2 = Agent.builder().firstName("Amy").lastName("Smith").email("amy.smith@gmail.com").build();
		
		a1 = agentService.create(a1, false);
		a2 = agentService.create(a2, false);
		
		log.info(a1.toString());
		log.info(a2.toString());
	}
	
	private void createCustomers() {
		if (customerService.count() > 0) {
			return;
		}
		
		Customer customer1 = Customer.builder().firstName("Bill").lastName("Williams")
				.email("bill.williams@outlook.com").phone("3133329900").build();
		customer1 = customerService.create(customer1);
		
		Address addr1 = Address.builder().city("Houston").state("Texas").build();
		AddressTO addr1TO = new AddressTO();
		BeanUtils.copyProperties(addr1, addr1TO);
		addr1TO.setCustomerId(customer1.getId());
		addr1 = addressService.create(addr1TO);
		
		log.info(customer1.toString());
	}
	
	private void createAppointment() {
		Customer customer = null;
		Agent agent = null;
		
		if (customerService.count() > 0 && agentService.count() > 0) {
			customer = customerService.getAll().get(0);
			agent = agentService.getAll().get(0);
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(2022, 6, 20, 11, 0);
			Date start = calendar.getTime();
			calendar.set(2022, 6, 20, 12, 0);
			Date end = calendar.getTime();
			
			Appointment appointment = Appointment.builder().title("Dental Appoitment")
					.description("Make sure it's on time. Call to cancel in case!")
					.startTime(start).endTime(end)
					.agent(agent).customer(customer).build();
			
			appointment = appointmentService.create(appointment);
		}
	}
}
