package com.ecommerence.onlineshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerence.onlineshop.model.AddToCartProduct;
import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.model.customer;

@Repository
public interface AddToCartProductRepository extends JpaRepository<AddToCartProduct, Long> {
	List<AddToCartProduct> findByCustomer(customer customer);

	AddToCartProduct findByProductAndCustomer(Product product, customer customer);

	void deleteByCustomer(customer customer);

}
