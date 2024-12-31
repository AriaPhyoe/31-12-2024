package com.ecommerence.onlineshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerence.onlineshop.model.Admin;
import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.model.shop;

@Repository
public interface ShopRepository extends JpaRepository<shop, Long> {

	shop findByEmailAndPassword(String email, String password);

	List<shop> findByShopname(String shopname);

	shop findByEmail(String email);

	shop findByShopId(Long shopId);

	Delivery save(Delivery delivery);

}
