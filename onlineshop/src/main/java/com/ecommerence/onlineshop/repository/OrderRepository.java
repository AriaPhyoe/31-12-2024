package com.ecommerence.onlineshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerence.onlineshop.model.OrderProduct;
import com.ecommerence.onlineshop.model.Orders;
import com.ecommerence.onlineshop.model.customer;

import jakarta.transaction.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
	List<OrderProduct> findByCustomer(customer customer);

	@Query("SELECT DISTINCT o FROM Orders o JOIN o.orderProduct oi JOIN oi.product i WHERE i.shop.id = :shopId")
	List<Orders> findOrderByShopId(@Param("shopId") Long shopId);

	@Modifying
	@Transactional
	@Query("UPDATE Orders o SET o.status = :status WHERE o.id = :orderId")
	void updateOrderStatus(@Param("orderId") Long orderId, @Param("status") boolean status);

}
