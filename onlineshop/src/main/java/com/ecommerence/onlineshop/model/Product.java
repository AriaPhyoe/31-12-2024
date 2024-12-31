package com.ecommerence.onlineshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productId;
	private String productName;
	private String category;
	private int productquantity;
	private double price;
	private String jpg;
	private String image;
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "shopId", nullable = false)
	private shop shop;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(String productName, String category, int productquantity, double price, String jpg, String image,
			String description, com.ecommerence.onlineshop.model.shop shop) {
		super();
		this.productName = productName;
		this.category = category;
		this.productquantity = productquantity;
		this.price = price;
		this.jpg = jpg;
		this.image = image;
		this.description = description;
		this.shop = shop;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getProductquantity() {
		return productquantity;
	}

	public void setProductquantity(int productquantity) {
		this.productquantity = productquantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getJpg() {
		return jpg;
	}

	public void setJpg(String jpg) {
		this.jpg = jpg;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public shop getShop() {
		return shop;
	}

	public void setShop(shop shop) {
		this.shop = shop;
	}
	

	

}
