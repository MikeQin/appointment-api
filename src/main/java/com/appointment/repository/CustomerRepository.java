package com.appointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.appointment.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByEmail(@Param("email") String email);
	List<Customer> findByFirstName(@Param("firstName") String name);
	List<Customer> findByLastName(@Param("lastName") String name);
}
