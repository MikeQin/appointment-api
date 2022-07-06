package com.appointment.model;

import com.appointment.entity.Address;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddressTO extends Address {

	private Long customerId;
}
