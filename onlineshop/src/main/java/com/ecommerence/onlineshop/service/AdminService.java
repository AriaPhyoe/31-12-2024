package com.ecommerence.onlineshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerence.onlineshop.model.Admin;
import com.ecommerence.onlineshop.model.Category;
import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.repository.AdminRepository;

@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	PasswordEncoder passwordEncoder;

	public List<Admin> getAllData() {
		// TODO Auto-generated method stub
		return adminRepo.findAll();
	}

	public Admin getAdminIdEdit(Long adminId) {
		return adminRepo.findByAdminId(adminId);
	}

	public boolean saveAdmin(Admin admin) {
		if (adminRepo.findByEmail(admin.getEmail()) == null) {
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			adminRepo.save(admin);
			return true;
		}
		return false;
	}

	public String checklogin(String email, String password) {
		Admin admin = adminRepo.findByEmail(email);
		if (admin != null) {
			if (passwordEncoder.matches(password, admin.getPassword()))
				;
			password = admin.getPassword();
			admin = adminRepo.findByEmailAndPassword(email, password);
			if (admin == null) {
				return "adminLogin";
			}

		}
		return "redirect:/admin/main";
	}

	public Admin getAdminById(Long id) {
		// Make sure 'id' is not null before calling findById
		if (id == null) {
			throw new IllegalArgumentException("ID must not be null");
		}
		return adminRepo.findById(id).orElse(null);
	}

	public Admin getAdminByEmail(String email) {
		// TODO Auto-generated method stub
		return adminRepo.findByEmail(email);
	}

	public List<Admin> getAllAdminList() {
		// TODO Auto-generated method stub
		return adminRepo.findAll();
	}

	public void updateAdmin(long id, Admin admin) {
		admin.setAdminId(id);
		adminRepo.save(admin);
	}

}
