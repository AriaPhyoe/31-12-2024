package com.ecommerence.onlineshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.DeliveryDetail;
import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.service.DeliveryDetailService;
import com.ecommerence.onlineshop.service.DeliveryService;
import com.ecommerence.onlineshop.service.ShopService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/delisys")
public class DeliveryController {

	@Autowired
	private DeliveryService deliSer;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private ShopService shopSer;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private DeliveryDetailService delidetailSer;

	@GetMapping("/index") // request name or endpoint
	public String startPage() {
		return "deliveryLogin"; // index.html view page
	}

	@GetMapping("/logout")
	public String logout() {
		// Invalidate session
		httpSession.invalidate();
		return "redirect:/delisys/index";
	}

	@GetMapping("/main") // request name or endpoint
	public String mainPage(HttpSession httpSession) {
		Long deliveryid = (long) httpSession.getAttribute("deliveryid");
		return "deliveryMainPage"; // index.html view page
	}

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("delivery", new Delivery());
		return "deliveryLogin";
	}

	@PostMapping("/signUpprocess")
	public String addProcess(@ModelAttribute("delivery") Delivery d) {
		deliSer.saveDelivery(d);
		return "redirect:/delisys/signup";
	}

	@PostMapping("/loginprocess")
	public String loginForm(@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password, Model model, HttpSession httpSession) {
		String result = deliSer.checkLogin(email, password);
		if ("redirect:/delisys/main".equals(result)) {
			Delivery delivery = deliSer.getCustomerByEmail(email);
			if (delivery != null) {
				httpSession.setAttribute("deliveryEmail", delivery.getDeliveryemail());
				httpSession.setAttribute("deliveryid", delivery.getDeliveryid());
				return "redirect:/delisys/main";
			}
		} else {
			model.addAttribute("loginError", "Invalid email or password, or account not enabled");
		}
		return "deliveryLogins";
	}

	@GetMapping("/deliveryregister")
	public String showRegistrationForm(Model model) {
		model.addAttribute("delivery", new Delivery());
		return "deliveryRequestForm";
	}

	@PostMapping("/registerprocess")
	public String registerProcess(@ModelAttribute("delivery") Delivery deli, Model model, HttpSession session) {
		Long shopid = (Long) session.getAttribute("shopId");
		shop shop = shopSer.findShopByshopId(shopid);
		if (deliSer.findByEmail(deli.getDeliveryemail()) != null) {
			model.addAttribute("registerError", "Email already in use.");
			return "deliveryLogin";
		}
		deli.setPassword(passwordEncoder.encode(deli.getPassword()));
		deli.setVerified(true);
		deli.setShop(shop);
		deliSer.save(deli);
		model.addAttribute("registerSuccess", "Registration successful! Please wait for admin approval.");
		return "redirect:/delisys/index";
	}

	@GetMapping("/checkdeliveryorderrequest")
	public String showdeliveryorderRequests(Model model, HttpSession httpSession) {
		Long shopId = (Long) httpSession.getAttribute("shopId");
		if (shopId != null) {
			shop shop = shopSer.findById(shopId);
			List<DeliveryDetail> delidetail = delidetailSer.getDeliveriesOrderByShopAdmin(shop);
			model.addAttribute("delidetail", delidetail);
		} else {
			model.addAttribute("orderError", "Shop ID not found in session.");
		}
		return "deliveryOrdersByDeli";
	}

}
