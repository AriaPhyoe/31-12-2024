package com.ecommerence.onlineshop.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long orderId;
	
	@ManyToOne
    @JoinColumn(name = "customerId")
    private customer customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProduct;
    private int noorderitems;
    private double totalamount;
    private String paymenttype;
    private LocalDateTime datetime;
    private String customeraddress;
    private boolean status = false;
	public Orders() { 
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Orders(com.ecommerence.onlineshop.model.customer customer, List<OrderProduct> orderProduct, int noorderitems,
			double totalamount, String paymenttype, LocalDateTime datetime, String customeraddress, boolean status) {
		super();
		this.customer = customer;
		this.orderProduct = orderProduct;
		this.noorderitems = noorderitems;
		this.totalamount = totalamount;
		this.paymenttype = paymenttype;
		this.datetime = datetime;
		this.customeraddress = customeraddress;
		this.status = status;
	}

	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public customer getCustomer() {
		return customer;
	}
	public void setCustomer(customer customer) {
		this.customer = customer;
	}
	public List<OrderProduct> getOrderProduct() {
		return orderProduct;
	}
	public void setOrderProduct(List<OrderProduct> orderProduct) {
		this.orderProduct = orderProduct;
	}
	public int getNoorderitems() {
		return noorderitems;
	}
	public void setNoorderitems(int noorderitems) {
		this.noorderitems = noorderitems;
	}
	public double getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	public String getCustomeraddress() {
		return customeraddress;
	}
	public void setCustomeraddress(String customeraddress) {
		this.customeraddress = customeraddress;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
		
	
	

}