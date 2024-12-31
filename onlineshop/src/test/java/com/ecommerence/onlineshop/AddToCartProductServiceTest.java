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

import com.ecommerence.onlineshop.model.AddToCartProduct;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.repository.AddToCartProductRepository;
import com.ecommerence.onlineshop.service.AddToCartProductService;

class AddToCartProductServiceTest {

    @Mock
    private AddToCartProductRepository cartRepo;

    @InjectMocks
    private AddToCartProductService cartService;

    private customer customer;
    private Product product;
    private AddToCartProduct cartProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new customer(); // Assuming Customer has a no-arg constructor
        product = new Product();   // Assuming Product has a no-arg constructor
        cartProduct = new AddToCartProduct(product, customer, 2); // 2 as productQty
    }

    @Test
    void testGetAddToCartProductByCustomerId() {
        // TC01
        when(cartRepo.findByCustomer(customer)).thenReturn(Arrays.asList(cartProduct));

        List<AddToCartProduct> result = cartService.getAddToCartProductByCustomerId(customer);

        assertEquals(1, result.size());
        assertEquals(cartProduct, result.get(0));
        verify(cartRepo, times(1)).findByCustomer(customer);
    }

    @Test
    void testAddProductToCart() {
        // TC02
        cartService.addProductToCart(product, customer, 3);

        verify(cartRepo, times(1)).save(any(AddToCartProduct.class));
    }

    @Test
    void testGetAddToCartProductByProductId() {
        // TC03
        when(cartRepo.findByProductAndCustomer(product, customer)).thenReturn(cartProduct);

        AddToCartProduct result = cartService.getAddToCartProductByProductId(product, customer);

        assertNotNull(result);
        assertEquals(cartProduct, result);
        verify(cartRepo, times(1)).findByProductAndCustomer(product, customer);
    }

    @Test
    void testRemoveCartProduct() {
        // TC04
        cartService.removeCartProduct(1L);

        verify(cartRepo, times(1)).deleteById(1L);
    }

    @Test
    void testSave() {
        // TC05
        cartService.save(cartProduct);

        verify(cartRepo, times(1)).save(cartProduct);
    }

    @Test
    void testRemoveCartProductByCustomer() {
        // TC06
        cartService.removeCartProductByCustomer(customer);

        verify(cartRepo, times(1)).deleteByCustomer(customer);
    }

    @Test
    void testAddProductToCartWithInvalidQuantity() {
        // TC07
        // Simulate invalid quantity (e.g., -1 or 0)
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addProductToCart(product, customer, -1);
        });

        assertEquals("Quantity must be greater than zero", thrown.getMessage());
    }
}
