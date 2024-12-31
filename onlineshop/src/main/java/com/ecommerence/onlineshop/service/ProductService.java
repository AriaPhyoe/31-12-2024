package com.ecommerence.onlineshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;

	@Transactional
	public void saveProduct(Product product) {
		productRepo.save(product);
	}

	public void deleteProduct(long id) {
		productRepo.deleteById(id);
	}

	public void updateProduct(long id, Product product) {
		product.setProductId(id);
		productRepo.save(product);
	}

	public void updateProductqty(long id, Product product) {
		product.setProductId(id);
		productRepo.save(product);
	}

	public List<Product> showAll() {
		return productRepo.findAll();
	}

	public Product getProduct(long id) {
		return productRepo.findById(id).get();
	}

	public List<Product> findByProductname(String productname) {
		return productRepo.findByProductName(productname);
	}

	public List<Product> findByProductCategory(String itemcategory) {
		return productRepo.findByCategory(itemcategory);
	}

	public Optional<Product> findById(long id) {
		return productRepo.findById(id);
	}

	public List<Product> findByShopId(Long shopId) {
		return productRepo.findByShop_shopId(shopId);
	}

	public Product getProductById(long itemId) {
		// TODO Auto-generated method stub
		return productRepo.findById(itemId).get();
	}

}
