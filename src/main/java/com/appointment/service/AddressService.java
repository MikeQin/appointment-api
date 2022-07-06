package com.appointment.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment.entity.Address;
import com.appointment.entity.Customer;
import com.appointment.entity.PersistentException;
import com.appointment.model.AddressTO;
import com.appointment.repository.AddressRepository;
import com.appointment.repository.CustomerRepository;

@Service
public class AddressService {
	
	@Autowired
	AddressRepository repo;
	@Autowired
	CustomerRepository customerRepo;
	
	/**
	 * Use TO to get customerId, and set it on entity.
	 * @param addressTO
	 * @return
	 */
	public Address create(AddressTO addressTO) {
		
		Customer customer = customerRepo.findById(addressTO.getCustomerId()).get();
		Address address = Address.builder().build();
		BeanUtils.copyProperties(addressTO, address);
		address.setCustomer(customer);
		address = repo.save(address);
		
		return address;
	}
	
	public Address update(Long id, AddressTO addressTO) {

		Address entity = null;
		try {
			entity = repo.findById(id).get();
		} catch (IllegalArgumentException e) {
			throw new PersistentException(e.getMessage());
		}
		
		// Usually, we don't update the customer on address
		// Simple, delete the address with that customer
		// Here we update the customer on address for demo purpose
		if (addressTO.getCustomerId() != entity.getCustomer().getId()) {
			BeanUtils.copyProperties(addressTO, entity);
			Customer customer = customerRepo.findById(addressTO.getCustomerId()).get();	
			entity.setCustomer(customer);
		}
		else { // No change on relationship with customer
			BeanUtils.copyProperties(addressTO, entity);
		}

		entity = repo.save(entity);

		return entity;
	}

	public void delete(Long id) {

		try {
			repo.deleteById(id);
		} catch (IllegalArgumentException e) {
			throw new PersistentException(e.getMessage());
		}
	}

}
