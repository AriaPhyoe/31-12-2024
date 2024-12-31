package com.ecommerence.onlineshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerence.onlineshop.model.OrderProduct;
import com.ecommerence.onlineshop.model.Orders;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.repository.OrderRepository;


@Service
public class OrdersService {
	@Autowired
	private OrderRepository orderRepo;
	
	public Orders addOrder(Orders order) {
		// TODO Auto-generated method stub
		return orderRepo.save(order);
	}



	public List<OrderProduct> getOrderByCustomer(customer customer) {
		// TODO Auto-generated method stub
		return orderRepo.findByCustomer(customer);
	}


	public Orders getOrderById(long oid) {
		// TODO Auto-generated method stub
		return orderRepo.findById(oid).get();
	}
	public void updateOrderStatus(Long orderId, boolean status) {
        orderRepo.updateOrderStatus(orderId, status);
    }

    public Orders getOrderById(Long orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }


	public List<Orders> getOrdersByShop(Long shopadminid) {
        return orderRepo.findOrderByShopId(shopadminid);
    }
	
	public List<Orders> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepo.findAll();
	}



	public void save(Orders orders) {
		// TODO Auto-generated method stub
		orderRepo.save(orders);
	}



	public void update(Orders order) {
		// TODO Auto-generated method stub
		orderRepo.save(order);
	}



}
