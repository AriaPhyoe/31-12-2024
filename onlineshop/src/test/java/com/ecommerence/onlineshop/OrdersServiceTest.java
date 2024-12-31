package com.ecommerence.onlineshop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ecommerence.onlineshop.model.OrderProduct;
import com.ecommerence.onlineshop.model.Orders;
import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.repository.OrderRepository;
import com.ecommerence.onlineshop.service.OrdersService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersServiceTest {

    @InjectMocks
    private OrdersService ordersService;

    @Mock
    private OrderRepository orderRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrder() {
        Orders order = new Orders();
        when(orderRepo.save(order)).thenReturn(order);
        Orders savedOrder = ordersService.addOrder(order);
        assertEquals(order, savedOrder);
    }

    @Test
    public void testGetOrderById() {
        Long orderId = 1L;
        Orders order = new Orders();
        when(orderRepo.findById(orderId)).thenReturn(Optional.of(order));
        Orders retrievedOrder = ordersService.getOrderById(orderId);
        assertEquals(order, retrievedOrder);
    }

    @Test
    public void testUpdateOrderStatus() {
        Long orderId = 1L;
        boolean status = true;
        doNothing().when(orderRepo).updateOrderStatus(orderId, status);
        ordersService.updateOrderStatus(orderId, status);
        verify(orderRepo, times(1)).updateOrderStatus(orderId, status);
    }

    @Test
    public void testGetOrderByCustomer() {
        customer cust = new customer();
        List<OrderProduct> orderProducts = new ArrayList<>();
        when(orderRepo.findByCustomer(cust)).thenReturn(orderProducts);
        List<OrderProduct> retrievedOrders = ordersService.getOrderByCustomer(cust);
        assertEquals(orderProducts, retrievedOrders);
    }

    @Test
    public void testGetOrdersByShop() {
        Long shopId = 1L;
        List<Orders> ordersList = new ArrayList<>();
        when(orderRepo.findOrderByShopId(shopId)).thenReturn(ordersList);
        List<Orders> retrievedOrders = ordersService.getOrdersByShop(shopId);
        assertEquals(ordersList, retrievedOrders);
    }
}



