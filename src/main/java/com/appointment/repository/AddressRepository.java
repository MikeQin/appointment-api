package com.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.appointment.entity.Address;

@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {

}
