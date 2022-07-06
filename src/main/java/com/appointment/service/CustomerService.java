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
	
	public Customer getByEmail(String email) {		
		return repo.findByEmail(email);
	}
	
	public List<Customer> getByFirstName(String firstName) {
		return repo.findByFirstName(firstName);
	}
	
	public List<Customer> getByLastName(String lastName) {
		return repo.findByFirstName(lastName);
	}
	
	public Customer create(Customer customer) {
		return create(customer, false);
	}
	
	public Customer create(Customer customer, boolean flush) {
		
		Customer customerEntity = null;		
		if (flush) {
			customerEntity = repo.saveAndFlush(customer);
		} else {
			customerEntity = repo.save(customer);
		}
		return customerEntity;
	}
	
	/**
	 * 
	 * @param id
	 * @param customer, the full copy of the original entity
	 * @return
	 */
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
	
	public void delete(Long id) {

		try {
			repo.deleteById(id);
		} catch (IllegalArgumentException e) {
			throw new PersistentException(e.getMessage());
		}
	}

}
