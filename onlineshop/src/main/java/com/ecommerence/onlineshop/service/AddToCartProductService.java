package com.ecommerence.onlineshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerence.onlineshop.model.AddToCartProduct;
import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.repository.AddToCartProductRepository;

import jakarta.transaction.Transactional;

@Service
public class AddToCartProductService {

	@Autowired
	private AddToCartProductRepository cartRepo;

	public List<AddToCartProduct> getAddToCartProductByCustomerId(customer customer) {
		return cartRepo.findByCustomer(customer);
	}

	public void addProductToCart(Product product, customer customer, int productqty) {
		AddToCartProduct addtocartitem = new AddToCartProduct(product, customer, productqty);
		cartRepo.save(addtocartitem);
	}

	public AddToCartProduct getAddToCartProductByProductId(Product product, customer customer) {
		return cartRepo.findByProductAndCustomer(product, customer);
	}

	public void removeCartProduct(long id) {
		cartRepo.deleteById(id);
	}

	public void save(AddToCartProduct product) {
		// TODO Auto-generated method stub
		cartRepo.save(product);

	}

	@Transactional
	public void removeCartProductByCustomer(customer customer) {
		cartRepo.deleteByCustomer(customer);
	}

}
