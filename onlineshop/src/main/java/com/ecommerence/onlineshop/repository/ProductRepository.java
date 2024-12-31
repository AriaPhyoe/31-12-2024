package com.ecommerence.onlineshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerence.onlineshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByProductName(String productName);

	List<Product> findByCategory(String category);

	List<Product> findByShop_shopId(Long shopId);
}
