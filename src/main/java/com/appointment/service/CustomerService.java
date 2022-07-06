package com.appointment.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.appointment.entity.Customer;
import com.appointment.entity.PersistentException;
import com.appointment.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repo;
	
	public CustomerService(CustomerRepository repo) {
		this.repo = repo;
	}
	
	public Customer create(Customer customer, boolean flush) {
		
		Customer customerEntity = repo.save(customer);
		
		if (flush) {
			customerEntity = repo.saveAndFlush(customer);
		} else {
			customerEntity = repo.save(customer);
		}
		return customerEntity;
	}
	
	public List<Customer> getAll() {
		List<Customer> customers = repo.findAll();
		
		return customers;
	}
	
	public Customer getById(Long id) {
		Customer customerEntity = null;
		
		try {
			customerEntity = repo.findById(id).get();
		} catch (IllegalArgumentException e) {
			throw new PersistentException(e.getMessage());
		}
		
		return customerEntity;
	}
	
	public Customer update(Long id, Customer customer) {
		
		Customer customerEntity = null;
		try {
			customerEntity = repo.findById(id).get();
		} catch (IllegalArgumentException e) {
			throw new PersistentException(e.getMessage());
		}
		
		BeanUtils.copyProperties(customer, customerEntity);
		customerEntity = repo.save(customerEntity);
		
		return customerEntity;
	}

}
