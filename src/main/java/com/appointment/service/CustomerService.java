package com.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.appointment.entity.Customer;
import com.appointment.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repo;
	
	public CustomerService(CustomerRepository repo) {
		this.repo = repo;
	}
	
	public Customer create(Customer customer) {
		
		Customer customerEntity = repo.save(customer);
		
		return customerEntity;
	}
	
	public List<Customer> getAll() {
		List<Customer> customers = repo.findAll();
		
		return customers;
	}

}
