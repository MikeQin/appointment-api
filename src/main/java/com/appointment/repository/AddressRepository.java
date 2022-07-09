package com.appointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.appointment.entity.Address;

@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findByCity(@Param("city") String city);
	List<Address> findByState(@Param("state") String city);
}
