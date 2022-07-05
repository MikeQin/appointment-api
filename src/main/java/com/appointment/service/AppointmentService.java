package com.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.appointment.entity.Appointment;
import com.appointment.repository.AppointmentRepository;

@Service
public class AppointmentService {

	private AppointmentRepository repo;

	public AppointmentService(AppointmentRepository repo) {
		this.repo = repo;
	}

	public Appointment create(Appointment appointment) {

		Appointment appointmentEntity = repo.save(appointment);
		return appointmentEntity;
	}

	public List<Appointment> getAll() {
		
		List<Appointment> appointments = repo.findAll();
		return appointments;
	}
	
	public Appointment getByTitle(String title) {
		
		Appointment appointment = repo.findByTitle(title);
		return appointment;
		
	}
}
