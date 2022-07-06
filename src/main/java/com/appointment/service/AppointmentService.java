package com.appointment.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.appointment.entity.Agent;
import com.appointment.entity.Appointment;
import com.appointment.entity.Customer;
import com.appointment.entity.PersistentException;
import com.appointment.model.AppointmentTO;
import com.appointment.repository.AgentRepository;
import com.appointment.repository.AppointmentRepository;
import com.appointment.repository.CustomerRepository;

@Service
public class AppointmentService {

	private AppointmentRepository repo;
	private AgentRepository agentRepo;
	private CustomerRepository customerRepo;

	public AppointmentService(AppointmentRepository repo,
			AgentRepository agentRepo,
			CustomerRepository customerRepo) {
		this.repo = repo;
		this.agentRepo = agentRepo;
		this.customerRepo = customerRepo;
	}

	public List<Appointment> getAll() {

		List<Appointment> appointments = repo.findAll();
		return appointments;
	}

	public Appointment getById(Long id) {

		Appointment appointment = null;
		try {
			appointment = repo.findById(id).get();

		} catch (IllegalArgumentException e) {
			throw new PersistentException(e.getMessage());
		}

		return appointment;
	}
	
	public Appointment getByTitle(String title) {

		return repo.findByTitle(title);
	}
	
	public Appointment getByStartTimeAndEndTime(Date startTime, Date endTime) {
		Appointment appointment = repo.findByStartTimeAndEndTime(startTime, endTime);

		return appointment;
	}

	public Appointment create(AppointmentTO appointmentTO) {
		return create(appointmentTO, false);
	}

	/**
	 * Use TO to get agentId and customerId in order to retrieve entities from DB first.
	 * 
	 * @param appointmentTO
	 * @param flush
	 * @return
	 */
	public Appointment create(AppointmentTO appointmentTO, boolean flush) {
		
		Agent agent = agentRepo.findById(appointmentTO.getAgentId()).get();
		Customer customer = customerRepo.findById(appointmentTO.getCustomerId()).get();
		
		Appointment appointment = Appointment.builder().build();
		BeanUtils.copyProperties(appointmentTO, appointment);
		appointment.setAgent(agent);
		appointment.setCustomer(customer);
		
		if (flush) {
			appointment = repo.saveAndFlush(appointment);
		} else {
			appointment = repo.save(appointment);
		}

		return appointment;
	}

	/**
	 * 
	 * @param id
	 * @param appointment, the full copy of the entity only
	 * @return
	 */
	public Appointment update(Long id, AppointmentTO appointmentTO) {
		Appointment entity = null;
		try {
			entity = repo.findById(id).get();
		} catch (IllegalArgumentException e) {
			throw new PersistentException(e.getMessage());
		}

		// Assume Customer and Agent on Appointment do not change
		// Otherwise, we need to handle the relationship update here
		BeanUtils.copyProperties(appointmentTO, entity);
		entity = repo.save(entity);

		return entity;

	}

	public void delete(Long id) {

		try {
			repo.deleteById(id);
		} catch (IllegalArgumentException e) {
			throw new PersistentException(e.getMessage());
		}
	}
}
