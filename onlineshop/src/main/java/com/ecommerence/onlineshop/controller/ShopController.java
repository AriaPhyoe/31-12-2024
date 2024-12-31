package com.ecommerence.onlineshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerence.onlineshop.model.Admin;
import com.ecommerence.onlineshop.model.Category;
import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.DeliveryDetail;
import com.ecommerence.onlineshop.model.Orders;
import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.repository.CategoryRepository;
import com.ecommerence.onlineshop.service.CategoryService;
import com.ecommerence.onlineshop.service.DeliveryDetailService;
import com.ecommerence.onlineshop.service.DeliveryService;
import com.ecommerence.onlineshop.service.OrdersService;
import com.ecommerence.onlineshop.service.ProductService;
import com.ecommerence.onlineshop.service.ShopService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private CategoryService categorySer;

	@Autowired
	private ProductService productSer;

	@Autowired
	private ShopService shopSer;

	@Autowired
	private OrdersService orderSer;

	@Autowired
	private DeliveryDetailService delidetailSer;

	@Autowired
	private DeliveryService deliSer;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/index") // request name or endpoint
	public String startPage() {
		return "shopAdminHome"; // index.html view page
	}

	@GetMapping("/updateproduct/{id}")
	public String updateProductPage(@PathVariable("id") long id, Model model) {
		model.addAttribute("product", productSer.getProduct(id));
		return "productupdatePageByShopadmin";
	}

	@PostMapping("/updateprocess/{id}")
	public String updateprocess(@PathVariable("id") Long id, @ModelAttribute("product") Product product, Model model,
			HttpSession httpSession) {
		Long shopId = (Long) httpSession.getAttribute("shopId");
		shop shop = shopSer.findShopByshopId(shopId);
		product.setShop(shop);
		productSer.updateProduct(id, product);
		return "redirect:/shop/productshow";
	}

	@GetMapping("/deleteproduct/{id}")
	public String DeleteProductPage(@PathVariable("id") Long id, Model model) {
		productSer.deleteProduct(id);
		return "redirect:/shop/productshow";
	}

	@GetMapping("/productshow")
	public String ProductShowPage(Model model, HttpSession httpSession) {
		Long shopId = (Long) httpSession.getAttribute("shopId");
		if (shopId == null) {
			return "redirect:/login";
		}
		shop shop = shopSer.findShopByshopId(shopId);
		if (shop == null) {
			return "redirect:/login";
		}
		List<Product> products = productSer.findByShopId(shopId);
		model.addAttribute("productlist", products);
		model.addAttribute("shop", shop);
		return "showProductByShopAdminPage";
	}
	
	@GetMapping("/productDelete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productSer.deleteProduct(id);
		return "redirect:/shop/productshow";
	}


	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("shop", new shop());
		return "shopAdminHome";
	}

	@PostMapping("/signUpprocess")
	public String addProcess(@ModelAttribute("shop") shop s) {
		shopSer.saveShop(s);
		return "redirect:/shop/signup";
	}


	@PostMapping("/loginprocess")
	public String loginForm(@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password, Model model, HttpSession httpSession) {
		String result = shopSer.checkLogin(email, password);
		if ("redirect:/shop/main".equals(result)) {
			shop shop = shopSer.getCustomerByEmail(email);
			if (shop != null) {
				httpSession.setAttribute("shopemail", shop.getEmail());
				httpSession.setAttribute("shopId", shop.getShopId());
				return "redirect:/shop/main";
			}
		} else {
			model.addAttribute("loginError", "Invalid email or password, or account not enabled");
		}
		return "shopAdminHome";
	}

	@GetMapping("/main") // request name or endpoint
	public String mainPage(HttpSession httpSession) {
		Long shopId = (long) httpSession.getAttribute("shopId");
		return "shopAdminMainPage"; // index.html view page
	}
	
	

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("shop", new shop());
		return "shopRegister";
	}

	@PostMapping("/registerprocess")
	public String registerProcess(@ModelAttribute("shop") shop shopAdmin, Model model) {
		if (shopSer.findByEmail(shopAdmin.getEmail()) != null) {
			model.addAttribute("registerError", "Email already in use.");
			return "RegisterFormPage";
		}
		shopAdmin.setPassword(passwordEncoder.encode(shopAdmin.getPassword()));
		shopAdmin.setVerified(false);
		shopSer.save(shopAdmin);
		model.addAttribute("registerSuccess", "Registration successful! Please wait for admin approval.");
		return "redirect:/shop/index";
	}

	@GetMapping("/productInsert")
	public String insertProductPage(Model model, HttpSession httpSession) {
		Long shopId = (Long) httpSession.getAttribute("shopId");
		if (shopId == null) {
			return "redirect:/login";
		}

		shop shopadmin = shopSer.findShopByshopId(shopId);
		if (shopadmin == null) {
			return "redirect:/error";
		}
		Product product = new Product();
		product.setShop(shopadmin);
		model.addAttribute("product", product);
		model.addAttribute("shop", shopadmin);
		model.addAttribute("shopId", shopId);
		return "addProductbyShopAdmin";
	}

	@PostMapping("/addProduct")
	public String addProductPage(@ModelAttribute("product") Product product, HttpSession httpSession) {
	    // Retrieve shopId from session
	    Long shopId = (Long) httpSession.getAttribute("shopId");

	    // Check if shopId is null, redirect to login if necessary
	    if (shopId == null) {
	        return "redirect:/login";
	    }

	    // Fetch the shopadmin by shopId
	    shop shopadmin = shopSer.findShopByshopId(shopId);

	    // Check if shopadmin is null, redirect to error page if necessary
	    if (shopadmin == null) {
	        return "redirect:/error";
	    }

	    // Ensure the category is set safely
	    if (shopadmin.getShopCatgory() == null) {
	        return "redirect:/error?message=Shop category not found";
	    }

	    // Set the shop and category for the product
	    product.setShop(shopadmin);
	    product.setCategory(shopadmin.getShopCatgory());

	    // Save the product
	    productSer.saveProduct(product);

	    // Redirect to the shop's main page
	    return "redirect:/shop/main";
	}
	

	@GetMapping("/logout")
	public String logout() {
		// Invalidate session
		httpSession.invalidate();
		return "redirect:/shop/login";
	}

	@GetMapping("/adminloginpage")
	public String startUserPage() {
		return "AdminLoginPage";
	}

	@GetMapping("/registerDelivery")
	public String registerDelivery(Model model) {
		model.addAttribute("delivery", new Delivery());
		return "deliveryRegistrationFormByShop";
	}

	@PostMapping("/registerprocessDelivery")
	public String registerProcess(@ModelAttribute("delivery") Delivery deli, Model model, HttpSession session ) {
		Long shopid = (Long) session.getAttribute("shopId");
		shop shop = shopSer.findShopByshopId(shopid);
		if (deliSer.findByEmail(deli.getDeliveryemail()) != null) {
			model.addAttribute("registerError", "Email already in use.");
			return "deliveryConfirmPageByShopAdmin";
		}
		deli.setPassword(passwordEncoder.encode(deli.getPassword()));
		deli.setVerified(true);
		deli.setShop(shop);
		deliSer.save(deli);
		model.addAttribute("registerSuccess", "Registration successful! Please wait for admin approval.");
		return "redirect:/shop/main";
	}

	@GetMapping("deliveryrequest")
	public String deliRequest(Model model) {
		List<Delivery> deli = deliSer.getAllDeliveryRequests();
		model.addAttribute("delivery", deli);
		return "deliveryConfirmPageByShopAdmin";
	}

	@PostMapping("/confirmrequest/{id}")
	public String confirmDeliveryRequest(@PathVariable("id") long deliveryid) {
		Delivery deli = deliSer.findById(deliveryid);
		if (deli != null) {
			deli.setVerified(true);
			deliSer.save(deli);
		}
		return "redirect:/shop/deliveryrequest";
	}

	@PostMapping("/deletedelivery/{id}")
	public String deleteShop(@PathVariable("id") long deliid) {
		deliSer.disableDelivery(deliid);
		return "redirect:/shop/deliveryrequest";
	}

	@GetMapping("/showorder")
	public String showOrderPage(Model model, HttpSession session) {
		Long shopId = (Long) session.getAttribute("shopId");
		if (shopId != null) {
			List<Orders> orders = orderSer.getOrdersByShop(shopId);
			model.addAttribute("orders", orders);
		} else {
			model.addAttribute("orderError", "ShopAdmin ID not found in session.");
		}
		return "ShowOrderPageByShopAdmin";
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
	    return "showDeliveryOrderByShopAdminPage";
	}
	
	/*
	 * @GetMapping("/showShopProfile") public String showShopProfile(Model model) {
	 * List<shop> shop = shopSer.getAllData(); model.addAttribute("shop", shop);
	 * return "shopProfile";
	 * 
	 * }
	 */
	
	@GetMapping("/showShopProfile")
	public String showShopProfile(Model model, HttpSession session) {
	    Long shopId = (Long) session.getAttribute("shopId");
	    if (shopId != null) {
	        // Fetch the specific shop by ID
	        Optional<shop> optionalShop = shopSer.getShopById(shopId); 
	        if (optionalShop.isPresent()) {
	            model.addAttribute("shop", optionalShop.get());
	        } else {
	            model.addAttribute("orderError", "Shop not found for ID: " + shopId);
	        }
	    } else {
	        model.addAttribute("orderError", "ShopAdmin ID not found in session.");
	    }
	    return "shopProfile";
	}

}
