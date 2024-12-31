package com.ecommerence.onlineshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerence.onlineshop.model.AddToCartProduct;
import com.ecommerence.onlineshop.model.Category;
import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.service.AddToCartProductService;
import com.ecommerence.onlineshop.service.CategoryService;
import com.ecommerence.onlineshop.service.CustomerService;
import com.ecommerence.onlineshop.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mainsys")
public class MainController {
	@Autowired
	private ProductService productSer;
	@Autowired
	private CustomerService customerSer;

	@Autowired
	private AddToCartProductService cartSer;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/homepage")
	public String ProductShowPage(Model model, @AuthenticationPrincipal customer customer) {
		model.addAttribute("productlist", productSer.showAll());
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("customer", customer);
		return "homePage";
	}

	@GetMapping("/search")
	public String searchItemPage(@RequestParam("productName") String productname, Model model) {
		List<Product> searchProducts = productSer.findByProductname(productname);
		model.addAttribute("searchProducts", searchProducts);
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "searchProduct";
	}

	@GetMapping("/searchcategory")
	public String searchCategoryItemPage(@RequestParam("category") String category, Model model) {
		List<Product> searchItems = productSer.findByProductCategory(category);
		model.addAttribute("searchProducts", searchItems);
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "searchCategory";
	}

	@PostMapping("/addToCart/{id}")
	public String addToCart(@PathVariable("id") long productId, @RequestParam("productQuantity") int productQuantity) {
	    Long customerId = (Long) httpSession.getAttribute("customerId");
 
	    if (customerId == null) {
	        return "redirect:/customer/login";
	    } else {
	        Product product = productSer.getProductById(productId);
	        customer customer = customerSer.getCustomerById(customerId);
 
	        if (product != null && customer != null) {
	            cartSer.addProductToCart(product, customer, productQuantity);
	            return "redirect:/mainsys/homepage";
	        } else {
	            return "redirect:/mainsys/homepage?error=ItemOrUserNotFound";
	        }
	    }
	}

	@GetMapping("/showCart")
	public String showCart(Model model, HttpSession session) {
		Long customerId = (Long) session.getAttribute("customerId");
		if (customerId == null) {
			return "redirect:/customer/login";
		}

		customer customer = customerSer.getCustomerById(customerId);
		if (customer == null) {
			return "redirect:/customer/login";
		}

		List<AddToCartProduct> cartProducts = cartSer.getAddToCartProductByCustomerId(customer);
		model.addAttribute("cartProducts", cartProducts);
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "addtocartCustomer";
	}

	@GetMapping("/showProfile")
	public String showProfile(Model model, HttpSession session) {
		Long customerId = (Long) session.getAttribute("customerId");
		if (customerId == null) {
			return "redirect:/customer/login";
		}

		customer customer = customerSer.getCustomerById(customerId); // Fixing the variable type to match the method
		if (customer == null) {
			return "redirect:/customer/login";
		}

		model.addAttribute("customer", customer); // Pass the customer object to the model
		return "CustomerProfile"; // Ensure the correct view is returned
	}

	

	@GetMapping("/productlist")
	public String Page(Model model) {
		return "addtocartitemlist";
	}

	@GetMapping("/showitem")
	public String showItemPage(Model model) {
		model.addAttribute("productlist", productSer.showAll());
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "showItem";
	}

	@GetMapping("/detailitem/{id}")
	public String getItemDetailPage(@PathVariable("id") long id, Model model) {
		Product product = productSer.getProductById(id);
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("product", product);
		return "productDetails";
	}

	@PostMapping("/itemdetail/{id}")
	public String readMorePage(@PathVariable("id") long id) {

		return "redirect:/mainsys/detailitem" + id;
	}

	@GetMapping("/removecartitem/{id}")
	public String removecartitem(@PathVariable("id") long id, HttpSession session) {
		Long customerId = (Long) session.getAttribute("customerId");
		cartSer.removeCartProduct(id);
		return "redirect:/mainsys/showCart";
	}

}
