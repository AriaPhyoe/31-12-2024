package com.ecommerence.onlineshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerence.onlineshop.model.Admin;
import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.model.shop;

import com.ecommerence.onlineshop.repository.ShopRepository;

@Service
public class ShopService {
	@Autowired
	private ShopRepository shopRepo;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	

	public boolean saveShop(shop shop) {
		if (shopRepo.findByEmail(shop.getEmail()) == null) {
			shop.setPassword(passwordEncoder.encode(shop.getPassword()));
			shopRepo.save(shop);
			return true;
		}
		return false;
	}

	public String checkRegister(String email, String password) {
		shop shopadmin = shopRepo.findByEmail(email);
		if (shopadmin != null) {
			if (shopadmin.getEmail() == null) {
				if (passwordEncoder.matches(password, shopadmin.getPassword())) {
					password = shopadmin.getPassword();
					shopadmin = shopRepo.findByEmailAndPassword(email, password);
					if (shopadmin == null) {
						return "adminlogin";
					}
				}
			}
		} else if (shopadmin.getEmail().equals(email)) {
			return "adminregister";
		}
		return "home";
	}

	public String checkLogin(String em, String pass) {
		shop admin = shopRepo.findByEmail(em);
		if (admin != null) {
			if (passwordEncoder.matches(pass, admin.getPassword())) {
				if (admin.isVerified()) {
					pass = admin.getPassword();
					admin = shopRepo.findByEmailAndPassword(em, pass);
					if (admin == null) {
						return "shopAdminHome";
					}
				} else {
					return "shopAdminHome";
				}
			}
		}
		return "redirect:/shop/main";
	}

	public shop getCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		return shopRepo.findByEmail(email);
	}

	public shop findShopByshopId(Long shopId) {
		return shopRepo.findById(shopId).get();
	}

	public List<shop> getAllShopRequests() {
		// TODO Auto-generated method stub
		return shopRepo.findAll();
	}

	public void save(shop shop) {
		// TODO Auto-generated method stub
		shopRepo.save(shop);

	}

	public shop findById(long shopId) {
		// TODO Auto-generated method stub
		return shopRepo.findById(shopId).get();
	}

	public shop findByEmail(String email) {
		// TODO Auto-generated method stub
		return shopRepo.findByEmail(email);
	}

	public void disableShop(Long shopId) {
		shop shopAdmin = shopRepo.findById(shopId).orElse(null);
		if (shopAdmin != null) {
			shopAdmin.setVerified(false);
			shopRepo.save(shopAdmin);
		}
	}
	
	public List<shop> getAllData() {
		// TODO Auto-generated method stub
		return shopRepo.findAll();
	}
	public Optional<shop> getShopById(Long shopId) {
        return shopRepo.findById(shopId);
    }

}
