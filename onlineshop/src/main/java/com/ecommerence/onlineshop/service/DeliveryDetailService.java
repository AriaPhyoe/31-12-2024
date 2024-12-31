package com.ecommerence.onlineshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.DeliveryDetail;
import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.repository.DeliveryDetailRepository;

@Service
public class DeliveryDetailService {
	@Autowired
	private DeliveryDetailRepository deliveryDetailRepo;

	public void save(DeliveryDetail deliveryDetail) {
		// TODO Auto-generated method stub
		deliveryDetailRepo.save(deliveryDetail);

	}

	public DeliveryDetail findById(long deliverydetailid) {
		// TODO Auto-generated method stub
		return deliveryDetailRepo.findById(deliverydetailid).get();
	}

	public List<DeliveryDetail> getDeliveriesOrderByDelivery(Delivery delivery) {
		// TODO Auto-generated method stub
		return deliveryDetailRepo.findByDelivery(delivery);
	}

	public List<DeliveryDetail> getDeliveriesOrderByShopAdmin(shop shop) {
		// TODO Auto-generated method stub
		return deliveryDetailRepo.findByShop(shop);
	}

}
