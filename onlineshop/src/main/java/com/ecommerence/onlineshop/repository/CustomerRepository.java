package com.ecommerence.onlineshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerence.onlineshop.model.customer;

@Repository
public interface CustomerRepository extends JpaRepository<customer, Long> {

	customer findByEmailAndPassword(String email, String password);

	customer findByEmail(String em);

}
