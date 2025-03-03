package com.ecommerence.onlineshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerence.onlineshop.model.Admin;
import com.ecommerence.onlineshop.model.Category;
import com.ecommerence.onlineshop.model.shop;
@Repository

public interface AdminRepository extends JpaRepository<Admin, Long> {
	List<Admin> findByFirstnameAndLastname(String firstname, String lastname);

	Admin findByEmailAndPassword(String email, String password);

	Admin findByEmail(String email);

	void save(shop shopadmin);

	Admin findByAdminId(Long adminId);

}
