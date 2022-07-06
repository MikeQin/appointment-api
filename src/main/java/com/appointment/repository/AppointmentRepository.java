package com.appointment.repository;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.appointment.entity.Appointment;

@Transactional
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	Appointment findByTitle(@Param("title") String title);
	
	Appointment findByStartTimeAndEndTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
