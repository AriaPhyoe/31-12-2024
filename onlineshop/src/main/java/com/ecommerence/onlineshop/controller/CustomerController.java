package com.ecommerence.onlineshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.service.CustomerService;
import com.ecommerence.onlineshop.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService cusSer;
	@Autowired
	private ProductService productSer;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private HttpSession httpSession;
	
	@GetMapping("/login")  // request name or endpoint
	public String startPage() {
		return "customerLoginPage"; //index.html view page
	}
	
	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("customer", new customer());
		return "CustomerSignupPage";
	}

	@PostMapping("/signUpprocess")
	public String addProcess(@ModelAttribute("customer") customer c) {
		if (cusSer.saveCustomer(c)) {
            return "redirect:/customer/signup"; // Redirect to the signup page on success
        }
        // Handle the case where saving the customer fails, e.g., display an error message
        return "errorPage"; // Replace with appropriate error handling
    }
	
	
	@GetMapping("/showProfile")
	public String showProfile(Model model, HttpSession session) {
		Long customerId = (Long) session.getAttribute("customerId");
		if (customerId == null) {
			return "redirect:/customer/login";
		}

		customer customer = cusSer.getCustomerById(customerId); // Fixing the variable type to match the method
		if (customer == null) {
			return "redirect:/customer/login";
		}

		model.addAttribute("customer", customer); // Pass the customer object to the model
		return "CustomerProfile"; // Ensure the correct view is returned
	}
	
	
	@PostMapping("/loginprocess")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model, 
			RedirectAttributes redirectAttributes) {
	    String result = cusSer.checklogin(email, password);
	    if ("redirect:/mainsys/homepage".equals(result)) {
	        customer customer = cusSer.getCustomerByEmail(email);
	        if (customer != null) {
	            
	    	    httpSession.setAttribute("customerEmail", customer.getEmail());
	            httpSession.setAttribute("customerId", customer.getCustomerId());
	            return "redirect:/mainsys/homepage"; // Redirect to index or appropriate page
	        }
	    } else {
	        model.addAttribute("loginError", "Invalid email or password");
	    }
	    return result;
	}
	
	@GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("customer", new customer());
        return "customerRegisterPage";
    }

	@PostMapping("/registerprocess")
	public String saveCustomer(@ModelAttribute("customer") customer customer) {
	    boolean isSaved = cusSer.saveCustomer(customer);

	    // If registration is successful, redirect to the login page
	    if (isSaved) {
	        return "redirect:/customer/login";
	    } 
	    // If registration fails, remain on the registration page
	    return "customerRegisterPage";
	}

	
    @GetMapping("/about")
	public String aboutPage(Model model) {
		return "about";
		
	}
    
    @GetMapping("/showProduct")
    public String showProductPage(Model model) {
        // Assuming you have a ProductService to fetch products
        List<Product> productList = productSer.showAll();
        
        // Add the list of products to the model
        model.addAttribute("productlist", productList);
        
        // Return the Thymeleaf template name
        return "productShowByCustomer";
    }
	
	
	

}
