package com.Tsuda.springboot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Tsuda.springboot.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public List<Customer> findAll();
	public Customer findByCustomerId(int customerId);
	public List<Customer> findByCustomername(String customername);

}
