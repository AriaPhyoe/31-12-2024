package com.ecommerence.onlineshop.controller;

import org.springframework.ui.Model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerence.onlineshop.model.Category;
import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.Admin;
import com.ecommerence.onlineshop.model.Orders;
import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.repository.ShopRepository;
import com.ecommerence.onlineshop.service.AdminService;
import com.ecommerence.onlineshop.service.CategoryService;
import com.ecommerence.onlineshop.service.CustomerService;
import com.ecommerence.onlineshop.service.DeliveryDetailService;
import com.ecommerence.onlineshop.service.DeliveryService;
import com.ecommerence.onlineshop.service.OrdersService;
import com.ecommerence.onlineshop.service.ProductService;
import com.ecommerence.onlineshop.service.ShopService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")

public class AdminController {

	@Autowired
	private ShopService shopSer;
	@Autowired
	private ShopRepository shopRepo;
	@Autowired
	private CategoryService categorySer;
	@Autowired
	private CustomerService cusSer;
	@Autowired
	private ProductService productSer;
	@Autowired
	private OrdersService orderSer;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AdminService adminSer;
	@Autowired
	private DeliveryDetailService delidetailSer;

	@Autowired
	private DeliveryService deliSer;
	@Autowired
	private HttpSession httpSession;

	@GetMapping("/login") // request name or endpoint
	public String startPage() {

		return "adminLogin"; // index.html view page
	}

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("admin", new Admin());
		return "adminSignup";
	}

	@PostMapping("/signUpprocess")
	public String addProcess(@ModelAttribute("admin") Admin a) {
		adminSer.saveAdmin(a);
		return "redirect:/admin/signup";
	}

	@PostMapping("/loginprocess")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession httpSession) {
		String result = adminSer.checklogin(email, password);
		if ("redirect:/admin/main".equals(result)) {
			Admin admin = adminSer.getAdminByEmail(email);
			if (admin != null) {

				httpSession.setAttribute("adminEmail", admin.getEmail());
				httpSession.setAttribute("adminId", admin.getAdminId());
				return "redirect:/admin/main"; // Redirect to index or appropriate page
			}
		} else {
			model.addAttribute("loginError", "Invalid email or password");
		}
		return "adminLogin";
	}
	
	

	@PostMapping("/registerprocessAdmin")
	public String saveAdmin(@ModelAttribute("admin") Admin admin) {
	    boolean isSaved = adminSer.saveAdmin(admin);

	    // If registration is successful, redirect to the login page
	    if (isSaved) {
	        return "redirect:/admin/login";
	    } 
	    // If registration fails, remain on the registration page
	    return "adminSignup";
	}

	@GetMapping("/main") // request name or endpoint
	public String mainPage(HttpSession httpSession) {
		Long adminId = (long) httpSession.getAttribute("adminId");
		return "mainPageAdmin"; // index.html view page
	}

	@GetMapping("/showshoprequest")
	public String showShopRequests(Model model) {
		List<shop> shop = shopSer.getAllShopRequests();
		model.addAttribute("shop", shop);
		return "manageShopAdmin";
	}

	@PostMapping("/confirmrequest/{id}")
	public String confirmShopRequest(@PathVariable("id") long shopId) {
		shop shop = shopSer.findById(shopId);
		if (shop != null) {
			shop.setVerified(true);
			shopSer.save(shop);
		}
		return "redirect:/admin/showshoprequest";
	}

	@GetMapping("/deleteShop/{id}")
	public String deleteShop(@PathVariable("id") long id) {
		shopSer.disableShop(id);
		return "redirect:/admin/showshoprequest";
	}

	@PostMapping("/deleteShopProcess/{id}")
	public String deleteShopProcess(@PathVariable("id") long shopId) {
		shopSer.disableShop(shopId);
		return "redirect:/admin/showshoprequest";
	}

	@GetMapping("/showcategories")
	public String listCategories(Model model) {
		List<Category> categories = categorySer.getAllCategories();
		model.addAttribute("categories", categories);
		return "showCategoryPageByAdmin";
	}

	@GetMapping("/addcategories")
	public String insertCategory(Model model) {
		model.addAttribute("category", new Category());
		return "addCategoryPageByAdmin";
	}

	@PostMapping("/savecategories")
	public String addCategory(Model model, @ModelAttribute("category") Category category) {
		categorySer.saveCategory(category);
		return "redirect:/admin/showcategories";

	}

	@GetMapping("/categoryDelete/{id}")
	public String deleteCategory(@PathVariable Long id) {
		categorySer.deleteCategory(id);
		return "redirect:/admin/showcategories";
	}

	@GetMapping("/showCustomer")
	public String showCustomerPage(Model model) {
		List<customer> customers = cusSer.getAllCustomerList();
		model.addAttribute("customers", customers);
		return "showCustomerListByAdmin";
	}

	@GetMapping("/showProduct")
	public String showProduct(Model model) {
		List<Product> products = productSer.showAll();
		model.addAttribute("products", products);
		return "showProductByAdmin";

	}

	@GetMapping("/showOrder")
	public String showOrder(Model model) {
		List<Orders> orders = orderSer.getAllOrders();
		model.addAttribute("orders", orders);
		return "showOrdersByAdmin";

	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("shop", new shop());
		return "shopRegistrationFormByAdmin";
	}

	@PostMapping("/registerprocess")
	public String registerProcess(@ModelAttribute("shop") shop shop, Model model) {
		if (shopSer.findByEmail(shop.getEmail()) != null) {
			model.addAttribute("registerError", "Email already in use.");
			return "manageShopAdmin";
		}
		shop.setPassword(passwordEncoder.encode(shop.getPassword()));
		shop.setVerified(false);
		shopSer.save(shop);
		model.addAttribute("registerSuccess", "Registration successful! Please wait for admin approval.");
		return "redirect:/admin/main";
	}

	@GetMapping("/showAdminProfile")
	public String showAdminProfile(Model model) {
		List<Admin> admin = adminSer.getAllData();
		model.addAttribute("admin", admin);
		return "newadmin";

	}

	/*--------------------------*/
	@GetMapping("/showDelivery")
	public String showDelivery(Model model) {
		List<Delivery> delivery = deliSer.getAllDeliveryRequests();
		model.addAttribute("delivery", delivery);
		return "manageDeliveryPage";
	}

	@PostMapping("/confirmDelirequest/{id}")
	public String confirmDelirequest(@PathVariable("id") long deliveryid) {
		Delivery delivery = deliSer.findById(deliveryid);
		if (delivery != null) {
			delivery.setVerified(true);
			deliSer.save(delivery);
		}
		return "redirect:/admin/showDelivery";
	}

	@PostMapping("/deletedeliveryByAdmin/{id}")
	public String deletedeliveryByAdmin(@PathVariable("id") long deliid) {
		deliSer.disableDelivery(deliid);
		return "redirect:/shop/showDelivery";
	}
	/*--------------------------*/

}
