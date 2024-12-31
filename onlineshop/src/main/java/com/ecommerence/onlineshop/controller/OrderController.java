package com.ecommerence.onlineshop.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerence.onlineshop.model.AddToCartProduct;
import com.ecommerence.onlineshop.model.Category;
import com.ecommerence.onlineshop.model.OrderProduct;
import com.ecommerence.onlineshop.model.Orders;
import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.repository.CustomerRepository;
import com.ecommerence.onlineshop.repository.ProductRepository;
import com.ecommerence.onlineshop.service.AddToCartProductService;
import com.ecommerence.onlineshop.service.CategoryService;
import com.ecommerence.onlineshop.service.CustomerService;
import com.ecommerence.onlineshop.service.OrderProductService;
import com.ecommerence.onlineshop.service.OrdersService;
import com.ecommerence.onlineshop.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private CustomerRepository cusRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CustomerService customerSer;
	@Autowired
	private AddToCartProductService addtocartproductSer;
	@Autowired
	private ProductService productSer;
	@Autowired
	private OrdersService orderSer;
	@Autowired
	private OrderProductService orderProductService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/checkpoint")
	public String checkPoint(Model model, HttpSession session) {
		Long customerId = (Long) session.getAttribute("customerId");
		if (customerId == null) {
			return "redirect:/customer/login";
		}
		customer customer = customerSer.getCustomerById(customerId);
		List<AddToCartProduct> addtoCartProducts = addtocartproductSer.getAddToCartProductByCustomerId(customer);
		int orderProductQty = addtoCartProducts.size();
		double totalAmount = calculateTotalAmount(addtoCartProducts);
		model.addAttribute("email", customer.getEmail());
		model.addAttribute("orderproductqty", orderProductQty);
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("addtoCartProducts", addtoCartProducts);
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "checkpoint";
	}

	private double calculateTotalAmount(List<AddToCartProduct> products) {
		return products.stream().mapToDouble(product -> product.getProduct().getPrice() * product.getProductquality())
				.sum();
	}

	@GetMapping("/orderForm")
	public String getOrderForm(Model model, HttpSession session) {
		Long customerId = (Long) session.getAttribute("customerId");
		if (customerId == null) {
			return "redirect:/customer/login";
		}
		customer customer = customerSer.getCustomerById(customerId);
		List<AddToCartProduct> addToCartProducts = addtocartproductSer.getAddToCartProductByCustomerId(customer);
		double totalAmount = addToCartProducts.stream()
				.mapToDouble(product -> product.getProduct().getPrice() * product.getProductquality()).sum();
		Long orderId = generateOrderId();
		model.addAttribute("orderId", orderId);
		model.addAttribute("customer", customer);
		model.addAttribute("productId",
				addToCartProducts.isEmpty() ? 0 : addToCartProducts.get(0).getProduct().getProductId());
		model.addAttribute("orderproductqty",
				addToCartProducts.stream().mapToInt(AddToCartProduct::getProductquality).sum());
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("paymenttype", "Credit Card");
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);

		return "orderForm";
	}

	@PostMapping("/placeOrder")
	public String placeOrder(@RequestParam("paymenttype") String paymenttype,
			@RequestParam("datetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datetime,
			@RequestParam("customeraddress") String customeraddress, HttpSession session) {
		Long customerId = (Long) session.getAttribute("customerId");
		if (customerId == null) {
			return "redirect:/customer/login";
		}

		customer customer = customerSer.getCustomerById(customerId);
		List<AddToCartProduct> addtoCartProducts = addtocartproductSer.getAddToCartProductByCustomerId(customer);
		int noorderitems = addtoCartProducts.size();
		double totalAmount = addtoCartProducts.stream()
				.mapToDouble(product -> product.getProduct().getPrice() * product.getProductquality()).sum();

		/*
		 * Orders orders = new Orders(customer, null,noorderitems, totalAmount,
		 * paymenttype, datetime,customeraddress,false);
		 */
		Orders orders = new Orders(customer, null, noorderitems, totalAmount, paymenttype, null, customeraddress,
				false);

		List<OrderProduct> orderProduct = addtoCartProducts.stream().map(cartItem -> {
			Product product = cartItem.getProduct();
			OrderProduct orderproduct = new OrderProduct();
			orderproduct.setOrders(orders);
			orderproduct.setProduct(product);
			orderproduct.setQuantity(cartItem.getProductquality());
			orderproduct.setPrice(product.getPrice() * cartItem.getProductquality());
			return orderproduct;
		}).collect(Collectors.toList());
		orders.setOrderProduct(orderProduct);
		orders.setCustomeraddress(customeraddress);
		orderSer.addOrder(orders);
		addtocartproductSer.removeCartProductByCustomer(customer);
		return "redirect:/mainsys/homepage";
	}

	private Long generateOrderId() {

		return System.currentTimeMillis();
	}

	@PostMapping("/new/{id}")
	public String showOrderForm(@PathVariable("id") long id, @RequestParam("productqty") int productqty, Model model,
			HttpSession session) {
		Long customerId = (Long) session.getAttribute("customerId");
		if (customerId == null) {
			return "redirect:/customer/login";
		}
		customer customer = customerSer.getCustomerById(customerId);
		Product product = productSer.getProductById(id);
		if (product == null) {
			return "redirect:/itemsys/index";
		}
		double amount = product.getPrice() * productqty;
		System.out.println(productqty);
		int noorderitem = 1;
		OrderProduct orderProducts = new OrderProduct(null, product, productqty, amount);
		System.out.println("xsdahvoaedv,h" + orderProducts.getQuantity());
		model.addAttribute("orderProducts", orderProducts);
		model.addAttribute("customer", customer);
		model.addAttribute("product", product);
		model.addAttribute("orderproductqty", productqty);
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "orderForm";
	}

	@PostMapping("/save/{productId}")
	public String saveOrder(@PathVariable("productId") long productId,
			@RequestParam("datetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datetime,
			@RequestParam("paymenttype") String paymenttype, @RequestParam("quantity") int quantity,
			@RequestParam("customeraddress") String customeraddress, @ModelAttribute OrderProduct orderProducts,
			HttpSession session) {

		Long customerId = (Long) session.getAttribute("customerId");
		if (customerId == null) {
			return "redirect:/customer/login";
		}
		customer customer = customerSer.getCustomerById(customerId);
		Product product = productSer.getProductById(productId);
		if (product == null) {
			return "redirect:/itemsys/index";
		}
		int newQuantity = product.getProductquantity() - quantity;
		if (newQuantity < 0) {
			return "redirect:/itemsys/index";
		}
		product.setProductquantity(newQuantity);
		productSer.saveProduct(product);
		Orders orders = new Orders();
		orders.setPaymenttype(paymenttype);
		orders.setTotalamount(product.getPrice() * quantity);
		orders.setDatetime(datetime);
		orders.setNoorderitems(1);
		orders.setCustomeraddress(customeraddress);
		orderProducts.setOrders(orders);
		orderProducts.setProduct(product);
		orderProducts.setQuantity(quantity);
		orderProducts.setPrice(product.getPrice() * quantity);
		orders.setCustomer(customer);
		orders.setTotalamount(orders.getTotalamount());
		orders.setStatus(false);
		orderSer.addOrder(orders);

		orderProductService.addOrderProduct(orderProducts);
		return "redirect:/mainsys/homepage";
	}

	@GetMapping("/userhistory")
	public String userHistoryPage(Model model, HttpSession session) {
		Long customerId = (Long) session.getAttribute("customerId");
		if (customerId != null) {
			customer customer = customerSer.getCustomerById(customerId);
			List<OrderProduct> orderProducts = orderProductService.getOrderProductByCustomer(customerId);
			model.addAttribute("orderProducts", orderProducts);
			List<Category> categories = categoryService.getAllCategories();
			model.addAttribute("categories", categories);
		}
		return "CustomerHistory";
	}

}
