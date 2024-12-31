package com.ecommerence.onlineshop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.repository.ProductRepository;
import com.ecommerence.onlineshop.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        when(productRepo.save(product)).thenReturn(product);
        productService.saveProduct(product);
        verify(productRepo, times(1)).save(product);
    }

    @Test
    public void testDeleteProduct() {
        long productId = 1L;
        doNothing().when(productRepo).deleteById(productId);
        productService.deleteProduct(productId);
        verify(productRepo, times(1)).deleteById(productId);
    }

    @Test
    public void testUpdateProduct() {
        long productId = 1L;
        Product product = new Product();
        product.setProductId(productId);
        when(productRepo.save(product)).thenReturn(product);
        productService.updateProduct(productId, product);
        verify(productRepo, times(1)).save(product);
    }

    @Test
    public void testUpdateProductQuantity() {
        long productId = 1L;
        Product product = new Product();
        product.setProductId(productId);
        when(productRepo.save(product)).thenReturn(product);
        productService.updateProductqty(productId, product);
        verify(productRepo, times(1)).save(product);
    }

    @Test
    public void testShowAllProducts() {
        List<Product> productList = new ArrayList<>();
        when(productRepo.findAll()).thenReturn(productList);
        List<Product> retrievedProducts = productService.showAll();
        assertEquals(productList, retrievedProducts);
    }

    @Test
    public void testGetProductById() {
        long productId = 1L;
        Product product = new Product();
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        Product retrievedProduct = productService.getProduct(productId);
        assertEquals(product, retrievedProduct);
    }

    @Test
    public void testFindByProductName() {
        String productName = "Sample Product";
        List<Product> productList = new ArrayList<>();
        when(productRepo.findByProductName(productName)).thenReturn(productList);
        List<Product> retrievedProducts = productService.findByProductname(productName);
        assertEquals(productList, retrievedProducts);
    }

    @Test
    public void testFindByProductCategory() {
        String category = "Electronics";
        List<Product> productList = new ArrayList<>();
        when(productRepo.findByCategory(category)).thenReturn(productList);
        List<Product> retrievedProducts = productService.findByProductCategory(category);
        assertEquals(productList, retrievedProducts);
    }

    @Test
    public void testFindByShopId() {
        Long shopId = 1L;
        List<Product> productList = new ArrayList<>();
        when(productRepo.findByShop_shopId(shopId)).thenReturn(productList);
        List<Product> retrievedProducts = productService.findByShopId(shopId);
        assertEquals(productList, retrievedProducts);
    }

    @Test
    public void testGetProductByNonExistentId() {
        long productId = 999L;
        when(productRepo.findById(productId)).thenReturn(Optional.empty());
        Optional<Product> retrievedProduct = productService.findById(productId);
        assertFalse(retrievedProduct.isPresent());
    }
}
