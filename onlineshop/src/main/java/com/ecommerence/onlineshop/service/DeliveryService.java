package com.ecommerence.onlineshop.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.repository.DeliveryRepository;
import com.ecommerence.onlineshop.repository.ShopRepository;

@Service
public class DeliveryService {
	@Autowired
	private DeliveryRepository deliveryRepo;

	@Autowired
	private BCryptPasswordEncoder passEncode;

	@Autowired
	private ShopRepository shopRepo;

	public boolean saveDelivery(Delivery deli) {

		if (deliveryRepo.findByDeliveryemail(deli.getDeliveryemail()) == null) {
			deli.setPassword(passEncode.encode(deli.getPassword()));

			deliveryRepo.save(deli);
			return true;
		}
		return false;
	}

	public String checkRegister(String email, String password) {
		Delivery deli = deliveryRepo.findByDeliveryemail(email);
		if (deli != null) {
			if (deli.getDeliveryemail() == null) {
				if (passEncode.matches(password, deli.getPassword())) {
					password = deli.getPassword();
					deli = deliveryRepo.findByDeliveryemailAndPassword(email, password);
					if (deli == null) {
						return "adminlogin";
					}
				}
			}
		} else if (deli.getDeliveryemail().equals(email)) {
			return "adminregister";
		}
		return "home";
	}

	public String checkLogin(String em, String pass) {
		Delivery deli = deliveryRepo.findByDeliveryemail(em);
		if (deli != null) {
			if (passEncode.matches(pass, deli.getPassword())) {
				if (deli.isVerified()) {
					pass = deli.getPassword();
					deli = deliveryRepo.findByDeliveryemailAndPassword(em, pass);
					if (deli == null) {
						return "DeliveryLogin";
					}
				} else {
					return "DeliveryLogin";
				}
			}
		}
		return "redirect:/delisys/main";
	}

	public Delivery getCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		return deliveryRepo.findByDeliveryemail(email);
	}

	public Delivery findDeliveryBydeliveryid(long deliveryid) {
		// TODO Auto-generated method stub
		return deliveryRepo.findByDeliveryid(deliveryid);
	}

	public List<Delivery> getAllDeliveryRequests() {
		// TODO Auto-generated method stub
		return deliveryRepo.findAll();
	}

	public void save(Delivery delivery) {

		shopRepo.save(delivery);
	}

	public Delivery findById(long deliveryid) {
		// TODO Auto-generated method stub
		return deliveryRepo.findById(deliveryid).get();
	}

	public Object findByEmail(String email) {
		// TODO Auto-generated method stub
		return deliveryRepo.findByDeliveryemail(email);
	}

	public List<Delivery> getDeliveriesByShopAdminEmail(String shopadminemail) {
		shop shopAdmin = shopRepo.findByEmail(shopadminemail);
		if (shopAdmin != null) {
			return deliveryRepo.findByshop(shopAdmin);
		}
		return Collections.emptyList();
	}

	public Delivery findDeliveryBydeliveryemail(String deliveryemail) {
		// TODO Auto-generated method stub
		return deliveryRepo.findByDeliveryemail(deliveryemail);
	}

	public void disableDelivery(Long deliid) {
		Delivery deli = deliveryRepo.findById(deliid).orElse(null);
		if (deli != null) {
			deli.setVerified(false);
			deliveryRepo.save(deli);
		}
	}

}
