package com.ecommerence.onlineshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AddToCartProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cartproductid;

	@ManyToOne
	@JoinColumn(name = "productId", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "customerId", nullable = false)
	private customer customer;

	private int productquality;

	public AddToCartProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddToCartProduct(Product product, com.ecommerence.onlineshop.model.customer customer, int productquality) {
		super();
		this.product = product;
		this.customer = customer;
		this.productquality = productquality;
	}

	public long getCartproductid() {
		return cartproductid;
	}

	public void setCartproductid(long cartproductid) {
		this.cartproductid = cartproductid;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public customer getCustomer() {
		return customer;
	}

	public void setCustomer(customer customer) {
		this.customer = customer;
	}

	public int getProductquality() {
		return productquality;
	}

	public void setProductquality(int productquality) {
		this.productquality = productquality;
	}

}
