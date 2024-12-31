package com.ecommerence.onlineshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerence.onlineshop.model.Category;
import com.ecommerence.onlineshop.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepo;

	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}

	public Category getCategoryByCategoryId(Long categoryid) {
		return categoryRepo.findByCategoryId(categoryid);
	}

	public Category saveCategory(Category category) {
		return categoryRepo.save(category);
	}

	public void deleteCategory(Long categoryid) {
		categoryRepo.deleteById(categoryid);
	}

}
