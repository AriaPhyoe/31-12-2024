package com.ecommerence.onlineshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.DeliveryDetail;
import com.ecommerence.onlineshop.model.shop;


@Repository
public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetail, Long> {
	
	List<DeliveryDetail> findByDelidatetime(LocalDateTime delidatetime);

	List<DeliveryDetail> findByDelivery(Delivery delivery);

	List<DeliveryDetail> findByShop(shop shop);

}
