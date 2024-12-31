package com.ecommerence.onlineshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long deliveryid;
	private String firstname;
	private String lastname;
	private String deliveryname;
	private String deliveryemail;
	private String password;
	private String deliveryaddress;
	private String deliveryphonenumber;
	private boolean isVerified = false;
	@ManyToOne
	@JoinColumn(name = "email", nullable = false)
	private shop shop;

	
	public Delivery() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Delivery(String firstname, String lastname, String deliveryname, String deliveryemail, String password,
			String deliveryaddress, String deliveryphonenumber, boolean isVerified, shop shopAdmin) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.deliveryname = deliveryname;
		this.deliveryemail = deliveryemail;
		this.password = password;
		this.deliveryaddress = deliveryaddress;
		this.deliveryphonenumber = deliveryphonenumber;
		this.isVerified = isVerified;
		this.shop = shop;
	}

	public long getDeliveryid() {
		return deliveryid;
	}

	public void setDeliveryid(long deliveryid) {
		this.deliveryid = deliveryid;
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

	public String getDeliveryname() {
		return deliveryname;
	}

	public void setDeliveryname(String deliveryname) {
		this.deliveryname = deliveryname;
	}

	public String getDeliveryemail() {
		return deliveryemail;
	}

	public void setDeliveryemail(String deliveryemail) {
		this.deliveryemail = deliveryemail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeliveryaddress() {
		return deliveryaddress;
	}

	public void setDeliveryaddress(String deliveryaddress) {
		this.deliveryaddress = deliveryaddress;
	}

	public String getDeliveryphonenumber() {
		return deliveryphonenumber;
	}

	public void setDeliveryphonenumber(String deliveryphonenumber) {
		this.deliveryphonenumber = deliveryphonenumber;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public shop getShop() {
		return shop;
	}

	public void setShop(shop shop) {
		this.shop = shop;
	}

}
