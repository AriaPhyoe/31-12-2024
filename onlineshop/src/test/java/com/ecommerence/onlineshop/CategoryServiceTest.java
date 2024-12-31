package com.ecommerence.onlineshop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecommerence.onlineshop.model.Category;
import com.ecommerence.onlineshop.repository.CategoryRepository;
import com.ecommerence.onlineshop.service.CategoryService;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepo;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();  // Assuming Category has a no-arg constructor
        category.setCategoryId(1L);
        category.setCategoryName("Electronics");
    }

    @Test
    void testGetAllCategories() {
        // TC01: Get all categories
        when(categoryRepo.findAll()).thenReturn(Arrays.asList(category));

        List<Category> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(category, result.get(0));
        verify(categoryRepo, times(1)).findAll();
    }

    @Test
    void testGetCategoryByCategoryId() {
        // TC02: Get category by ID
        when(categoryRepo.findByCategoryId(1L)).thenReturn(category);

        Category result = categoryService.getCategoryByCategoryId(1L);

        assertNotNull(result);
        assertEquals(category, result);
        verify(categoryRepo, times(1)).findByCategoryId(1L);
    }

    @Test
    void testGetCategoryByCategoryIdInvalidId() {
        // TC03: Get category by invalid ID
        when(categoryRepo.findByCategoryId(99L)).thenReturn(null);

        Category result = categoryService.getCategoryByCategoryId(99L);

        assertNull(result);
        verify(categoryRepo, times(1)).findByCategoryId(99L);
    }

    @Test
    void testSaveCategory() {
        // TC04: Save a category
        when(categoryRepo.save(category)).thenReturn(category);

        Category result = categoryService.saveCategory(category);

        assertNotNull(result);
        assertEquals(category, result);
        verify(categoryRepo, times(1)).save(category);
    }

    @Test
    void testSaveCategoryWithMissingFields() {
        // TC05: Save a category with missing fields
        category.setCategoryName(null);  // Simulate missing field

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.saveCategory(category);
        });

        assertEquals("Category name cannot be null", exception.getMessage());
    }

    @Test
    void testDeleteCategory() {
        // TC06: Delete a category by ID
        doNothing().when(categoryRepo).deleteById(1L);

        categoryService.deleteCategory(1L);

        verify(categoryRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCategoryInvalidId() {
        // TC07: Delete category by invalid ID
        doNothing().when(categoryRepo).deleteById(99L);

        categoryService.deleteCategory(99L);

        verify(categoryRepo, times(1)).deleteById(99L);
    }
}
