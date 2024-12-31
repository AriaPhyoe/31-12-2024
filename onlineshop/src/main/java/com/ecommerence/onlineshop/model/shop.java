package com.ecommerence.onlineshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class shop {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long shopId;
	private String firstname;
	private String lastname;
	private String email;
	private String shopname;
	private String shopCatgory;
	private String password;
	private String address;
	private String phoneNumber;
	private String description;
	private String ownerName;
	private boolean isVerified = false;

	public shop() {
		super();
		// TODO Auto-generated constructor stub
	}

	public shop(String firstname, String lastname, String email, String shopname, String shopCatgory, String password,
			String address, String phoneNumber, String description, String ownerName, boolean isVerified) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.shopname = shopname;
		this.shopCatgory = shopCatgory;
		this.password = password;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.ownerName = ownerName;
		this.isVerified = isVerified;
	}

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getShopCatgory() {
		return shopCatgory;
	}

	public void setShopCatgory(String shopCatgory) {
		this.shopCatgory = shopCatgory;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

}
