package com.appointment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ADDRESS")
public class Address {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@NotBlank
	private String city;
	@NotBlank
	private String state;

}
