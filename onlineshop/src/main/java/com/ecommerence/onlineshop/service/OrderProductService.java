package com.ecommerence.onlineshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerence.onlineshop.model.OrderProduct;
import com.ecommerence.onlineshop.repository.OrderProductRepository;

@Service
public class OrderProductService {
	@Autowired
	private OrderProductRepository orderProductRep;

	public List<OrderProduct> getOrderProductByCustomer(Long customerId) {
		return orderProductRep.findByCustomer(customerId);
	}

	public void addOrderProduct(OrderProduct OrderProduct) {
		// TODO Auto-generated method stub
		orderProductRep.save(OrderProduct);

	}

}
