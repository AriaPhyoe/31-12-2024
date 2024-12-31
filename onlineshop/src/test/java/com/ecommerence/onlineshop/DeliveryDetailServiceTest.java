package com.ecommerence.onlineshop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.DeliveryDetail;
import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.repository.DeliveryDetailRepository;
import com.ecommerence.onlineshop.service.DeliveryDetailService;

class DeliveryDetailServiceTest {

    @Mock
    private DeliveryDetailRepository deliveryDetailRepo;

    @InjectMocks
    private DeliveryDetailService deliveryDetailService;

    private DeliveryDetail deliveryDetail;
    private Delivery delivery;
    private shop shop;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        delivery = new Delivery();  // Assuming Delivery has a no-arg constructor
        shop = new shop();          // Assuming Shop has a no-arg constructor
        deliveryDetail = new DeliveryDetail(); // Assuming DeliveryDetail has a no-arg constructor
    }

    @Test
    void testSave() {
        // TC01: Save a delivery detail
        deliveryDetailService.save(deliveryDetail);

        verify(deliveryDetailRepo, times(1)).save(deliveryDetail);
    }

    @Test
    void testFindById() {
        // TC02: Find delivery detail by ID
        when(deliveryDetailRepo.findById(1L)).thenReturn(Optional.of(deliveryDetail));

        DeliveryDetail result = deliveryDetailService.findById(1L);

        assertNotNull(result);
        assertEquals(deliveryDetail, result);
        verify(deliveryDetailRepo, times(1)).findById(1L);
    }

    @Test
    void testFindByIdWithInvalidId() {
        // TC06: Find delivery detail by invalid ID
        when(deliveryDetailRepo.findById(99L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            deliveryDetailService.findById(99L);
        });

        assertEquals("No value present", exception.getMessage());
    }

    @Test
    void testGetDeliveriesOrderByDelivery() {
        // TC03: Find delivery details by delivery
        when(deliveryDetailRepo.findByDelivery(delivery)).thenReturn(Arrays.asList(deliveryDetail));

        List<DeliveryDetail> result = deliveryDetailService.getDeliveriesOrderByDelivery(delivery);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(deliveryDetail, result.get(0));
        verify(deliveryDetailRepo, times(1)).findByDelivery(delivery);
    }

    @Test
    void testGetDeliveriesOrderByShopAdmin() {
        // TC04: Find delivery details by shop admin
        when(deliveryDetailRepo.findByShop(shop)).thenReturn(Arrays.asList(deliveryDetail));

        List<DeliveryDetail> result = deliveryDetailService.getDeliveriesOrderByShopAdmin(shop);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(deliveryDetail, result.get(0));
        verify(deliveryDetailRepo, times(1)).findByShop(shop);
    }

    @Test
    void testGetDeliveriesOrderByShopAdminWithNoMatches() {
        // TC07: Find delivery details by shop admin with no matching deliveries
        when(deliveryDetailRepo.findByShop(shop)).thenReturn(Collections.emptyList());

        List<DeliveryDetail> result = deliveryDetailService.getDeliveriesOrderByShopAdmin(shop);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deliveryDetailRepo, times(1)).findByShop(shop);
    }

    @Test
    void testSaveDeliveryDetailWithNullDelivery() {
        // TC05: Save a delivery detail with null delivery
        deliveryDetail.setDelivery(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deliveryDetailService.save(deliveryDetail);
        });

        assertEquals("Delivery cannot be null", exception.getMessage());
    }
}
