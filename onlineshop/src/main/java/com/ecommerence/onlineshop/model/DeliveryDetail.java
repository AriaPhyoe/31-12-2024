package com.ecommerence.onlineshop.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class DeliveryDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long deliverydetailId;
	
	@ManyToOne
    @JoinColumn(name = "orderId")
    private Orders orders;
	
	@ManyToOne
    @JoinColumn(name = "deliveryemail")
    private Delivery delivery;
	@ManyToOne
    @JoinColumn(name = "shopId")
    private shop shop;
	
	private LocalDateTime delidatetime;
	private boolean status = false;
	public DeliveryDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DeliveryDetail(Orders orders, Delivery delivery, shop shopAdmin, LocalDateTime delidatetime,
			boolean status) {
		super();
		this.orders = orders;
		this.delivery = delivery;
		this.shop = shopAdmin;
		this.delidatetime = delidatetime;
		this.status = status;
	}
	public long getDeliverydetailId() {
		return deliverydetailId;
	}
	public void setDeliverydetailId(long deliverydetailId) {
		this.deliverydetailId = deliverydetailId;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public Delivery getDelivery() {
		return delivery;
	}
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	
	public shop getShop() {
		return shop;
	}
	public void setShop(shop shop) {
		this.shop = shop;
	}
	public LocalDateTime getDelidatetime() {
		return delidatetime;
	}
	public void setDelidatetime(LocalDateTime delidatetime) {
		this.delidatetime = delidatetime;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
	

}
