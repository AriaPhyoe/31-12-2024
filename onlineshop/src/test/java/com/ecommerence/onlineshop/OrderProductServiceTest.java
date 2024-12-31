package com.ecommerence.onlineshop;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ecommerence.onlineshop.model.OrderProduct;
import com.ecommerence.onlineshop.model.Product;
import com.ecommerence.onlineshop.model.Orders;
import com.ecommerence.onlineshop.repository.OrderProductRepository;
import com.ecommerence.onlineshop.service.OrderProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

public class OrderProductServiceTest {

    @InjectMocks
    private OrderProductService orderProductService;

    @Mock
    private OrderProductRepository orderProductRepository;

    private OrderProduct orderProduct;
    private Product product;
    private Orders orders;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orders = new Orders(); // Create a valid Orders object
        product = new Product(); // Create a valid Product object
        orderProduct = new OrderProduct(orders, product, 2, 19.99); // Initialize with valid values
    }

    @Test
    public void testAddOrderProduct() {
        when(orderProductRepository.save(any(OrderProduct.class))).thenReturn(orderProduct);
        
        orderProductService.addOrderProduct(orderProduct);
        
        verify(orderProductRepository, times(1)).save(orderProduct);
    }

    @Test
    public void testAddOrderProduct_NullProduct() {
        OrderProduct invalidOrderProduct = new OrderProduct(null, product, 2, 19.99);
        Exception exception = assertThrows(Exception.class, () -> {
            orderProductService.addOrderProduct(invalidOrderProduct);
        });
        
        assertNotNull(exception); // Adjust the assertion based on your actual exception handling
    }

    @Test
    public void testAddOrderProduct_NullOrder() {
        OrderProduct invalidOrderProduct = new OrderProduct(orders, null, 2, 19.99);
        Exception exception = assertThrows(Exception.class, () -> {
            orderProductService.addOrderProduct(invalidOrderProduct);
        });

        assertNotNull(exception); // Adjust the assertion based on your actual exception handling
    }

    @Test
    public void testGetOrderProductByCustomer() {
        Long customerId = 1L; // Assume this is a valid customer ID
        when(orderProductRepository.findByCustomer(customerId)).thenReturn(Collections.singletonList(orderProduct));
        
        List<OrderProduct> result = orderProductService.getOrderProductByCustomer(customerId);
        assertEquals(1, result.size());
        assertEquals(orderProduct, result.get(0));
    }

    @Test
    public void testGetOrderProductByCustomer_NoProducts() {
        Long customerId = 2L; // Assume no products exist for this ID
        when(orderProductRepository.findByCustomer(customerId)).thenReturn(Collections.emptyList());
        
        List<OrderProduct> result = orderProductService.getOrderProductByCustomer(customerId);
        assertTrue(result.isEmpty());
    }


    @Test
    public void testAddOrderProduct_NegativeQuantity() {
        OrderProduct invalidOrderProduct = new OrderProduct(orders, product, -1, 19.99);
        Exception exception = assertThrows(Exception.class, () -> {
            orderProductService.addOrderProduct(invalidOrderProduct);
        });

        assertNotNull(exception); // Adjust the assertion based on your actual exception handling
    }

    @Test
    public void testAddOrderProduct_ZeroQuantity() {
        OrderProduct invalidOrderProduct = new OrderProduct(orders, product, 0, 19.99);
        orderProductService.addOrderProduct(invalidOrderProduct);
        verify(orderProductRepository, times(1)).save(invalidOrderProduct); // Assuming the system allows zero quantity
    }
}



