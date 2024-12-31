package com.ecommerence.onlineshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository cusRepo;
	@Autowired
	PasswordEncoder passwordEncoder;

	public boolean saveCustomer(customer customer) {
		if (cusRepo.findByEmail(customer.getEmail()) == null) {
			customer.setPassword(passwordEncoder.encode(customer.getPassword()));
			cusRepo.save(customer);
			return true;
		}
		return false;
	}

	public String checkRegister(String email, String password) {
		customer customer = cusRepo.findByEmail(email);
		if (customer != null) {
			if (customer.getEmail() == null) {
				if (passwordEncoder.matches(password, customer.getPassword())) {
					password = customer.getPassword();
					customer = cusRepo.findByEmailAndPassword(email, password);
					if (customer == null) {
						return "CustomerSignupPage";
					}
				}
			}

		} else if (customer.getEmail().equals(email)) {
			return "signup";
		}
		return "redirect:/customer/login";
	}

	public String checklogin(String email, String password) {
		customer customer = cusRepo.findByEmail(email);
		if (customer != null) {
			if (passwordEncoder.matches(password, customer.getPassword()))
				;
			password = customer.getPassword();
			customer = cusRepo.findByEmailAndPassword(email, password);
			if (customer == null) {
				return "customerLoginPage";
			}

		}
		return "redirect:/mainsys/homepage";
	}

	public customer getCustomerById(Long customerId) {
		// TODO Auto-generated method stub
		return cusRepo.findById(customerId).get();
	}

	public customer getCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		return cusRepo.findByEmail(email);
	}

	public List<customer> getAllCustomerList() {
		// TODO Auto-generated method stub
		return cusRepo.findAll();
	}

}
