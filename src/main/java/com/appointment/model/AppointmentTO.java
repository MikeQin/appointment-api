package com.appointment.model;

import com.appointment.entity.Appointment;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <code>TO</code> stands for Transfer Object
 * @author mikeq
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AppointmentTO extends Appointment {

	private Long agentId;
	private Long customerId;
}
