package com.ecommerence.onlineshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.shop;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

	Delivery findByDeliveryemail(String deliveryemail);

	Delivery findByDeliveryemailAndPassword(String em, String pw);

	Delivery findByDeliveryid(long deliveryid);

	List<Delivery> findByshop(shop shop);

}
