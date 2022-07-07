package com.appointment.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.appointment.entity.Appointment;
import com.appointment.entity.PersistentException;
import com.appointment.model.AppointmentTO;
import com.appointment.service.AppointmentService;

@RestController
@RequestMapping(value = "/api/appointments")
public class AppointmentController {

	private AppointmentService service;

	public AppointmentController(AppointmentService service) {
		this.service = service;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> get(
			@RequestParam(name = "title", defaultValue = "", required = false) String title) {
		
		if (!title.isBlank()) {
			Appointment appointment = service.getByTitle(title);
			return ResponseEntity.ok(appointment);
		}
		
		List<Appointment> appointments = service.getAll();
		return ResponseEntity.ok(appointments);
	}

	/**
	 * 
	 * @param appointmentTO - Transfer Object
	 * @return
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> create(@RequestBody AppointmentTO appointmentTO) {
		
		Appointment entity = service.create(appointmentTO);
		
		if (validateDates(appointmentTO) == false) {		
			
			ClientValidationError error = ClientValidationError
					.builder().error("Start Date/Time can't be after End Date/Time").build();
			return ResponseEntity.badRequest().body(error);
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(entity.getId()).toUri();

		return ResponseEntity.created(location).body(entity);
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(@PathVariable(name = "id", required = true) Long id) {
		
		try {			
			service.delete(id);
			Status status = Status.builder().message("DELETE SUCCESS, ID: " + id).build();
			return ResponseEntity.ok(status);
			
		} catch (PersistentException e) {
			ClientValidationError error 
				= ClientValidationError.builder().error("Invalid ID [" + "]").build();
			return ResponseEntity.badRequest().body(error);
		}
	}
	
	private boolean validateDates(AppointmentTO to) {
		boolean valid = true;
		
		Date start = to.getStartTime();
		Date end = to.getEndTime();
		
		if (start.after(end)) {
			valid = false;
		}
		
		return valid;
	}
}
