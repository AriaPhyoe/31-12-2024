package com.ecommerence.onlineshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerence.onlineshop.model.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

	@Query("SELECT o FROM OrderProduct o WHERE o.orders.customer.customerId = :customerId")
	List<OrderProduct> findByCustomer(@Param("customerId") Long customerId);

}
