package com.ecommerence.onlineshop;



import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import com.ecommerence.onlineshop.model.Delivery;
import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.repository.DeliveryRepository;
import com.ecommerence.onlineshop.repository.ShopRepository;
import com.ecommerence.onlineshop.service.DeliveryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    @InjectMocks
    private DeliveryService deliveryService;

    @Mock
    private DeliveryRepository deliveryRepo;

    @Mock
    private ShopRepository shopRepo;

    @Mock
    private BCryptPasswordEncoder passEncode;

    private Delivery delivery;

    @BeforeEach
    public void setUp() {
        delivery = new Delivery();
        delivery.setDeliveryemail("test@example.com");
        delivery.setPassword("password");
        delivery.setVerified(true);
    }

    @Test
    public void testSaveDelivery_NewDelivery() {
        when(deliveryRepo.findByDeliveryemail(delivery.getDeliveryemail())).thenReturn(null);
        when(passEncode.encode(delivery.getPassword())).thenReturn("encodedPassword");

        boolean result = deliveryService.saveDelivery(delivery);

        assertTrue(result);
        verify(deliveryRepo).save(delivery);
    }

    @Test
    public void testSaveDelivery_ExistingEmail() {
        when(deliveryRepo.findByDeliveryemail(delivery.getDeliveryemail())).thenReturn(delivery);

        boolean result = deliveryService.saveDelivery(delivery);

        assertFalse(result);
        verify(deliveryRepo, never()).save(any());
    }

    @Test
    public void testCheckRegister_NewDelivery() {
        when(deliveryRepo.findByDeliveryemail(delivery.getDeliveryemail())).thenReturn(null);

        String result = deliveryService.checkRegister(delivery.getDeliveryemail(), delivery.getPassword());

        assertEquals("adminregister", result);
    }

    @Test
    public void testCheckRegister_RegisteredEmail() {
        when(deliveryRepo.findByDeliveryemail(delivery.getDeliveryemail())).thenReturn(delivery);

        String result = deliveryService.checkRegister(delivery.getDeliveryemail(), delivery.getPassword());

        assertEquals("home", result);
    }

    @Test
    public void testCheckLogin_ValidCredentials() {
        when(deliveryRepo.findByDeliveryemail(delivery.getDeliveryemail())).thenReturn(delivery);
        when(passEncode.matches(delivery.getPassword(), delivery.getPassword())).thenReturn(true);
        
        String result = deliveryService.checkLogin(delivery.getDeliveryemail(), delivery.getPassword());

        assertEquals("redirect:/delisys/main", result);
    }

    @Test
    public void testCheckLogin_InvalidCredentials() {
        when(deliveryRepo.findByDeliveryemail(delivery.getDeliveryemail())).thenReturn(delivery);
        when(passEncode.matches(delivery.getPassword(), "wrongPassword")).thenReturn(false);
        
        String result = deliveryService.checkLogin(delivery.getDeliveryemail(), "wrongPassword");

        assertEquals("DeliveryLogin", result);
    }

    @Test
    public void testCheckLogin_UnverifiedDelivery() {
        delivery.setVerified(false);
        when(deliveryRepo.findByDeliveryemail(delivery.getDeliveryemail())).thenReturn(delivery);
        when(passEncode.matches(delivery.getPassword(), delivery.getPassword())).thenReturn(true);
        
        String result = deliveryService.checkLogin(delivery.getDeliveryemail(), delivery.getPassword());

        assertEquals("DeliveryLogin", result);
    }

    @Test
    public void testGetCustomerByEmail() {
        when(deliveryRepo.findByDeliveryemail(delivery.getDeliveryemail())).thenReturn(delivery);

        Delivery result = deliveryService.getCustomerByEmail(delivery.getDeliveryemail());

        assertEquals(delivery, result);
    }

    @Test
    public void testFindDeliveryBydeliveryid() {
        when(deliveryRepo.findByDeliveryid(1L)).thenReturn(delivery);

        Delivery result = deliveryService.findDeliveryBydeliveryid(1L);

        assertEquals(delivery, result);
    }

    @Test
    public void testGetAllDeliveryRequests() {
        when(deliveryRepo.findAll()).thenReturn(Collections.singletonList(delivery));

        List<Delivery> result = deliveryService.getAllDeliveryRequests();

        assertEquals(1, result.size());
        assertEquals(delivery, result.get(0));
    }

    @Test
    public void testSave_DeliveryInShop() {
        when(shopRepo.save(delivery)).thenReturn(delivery);

        deliveryService.save(delivery);

        verify(shopRepo).save(delivery);
    }

    @Test
    public void testFindById() {
        when(deliveryRepo.findById(1L)).thenReturn(Optional.of(delivery));

        Delivery result = deliveryService.findById(1L);

        assertEquals(delivery, result);
    }

    @Test
    public void testFindByEmail() {
        when(deliveryRepo.findByDeliveryemail(delivery.getDeliveryemail())).thenReturn(delivery);

        Delivery result = (Delivery) deliveryService.findByEmail(delivery.getDeliveryemail());

        assertEquals(delivery, result);
    }

    @Test
    public void testGetDeliveriesByShopAdminEmail() {
        shop shopAdmin = new shop();
        shopAdmin.setEmail("shopadmin@example.com");
        when(shopRepo.findByEmail(shopAdmin.getEmail())).thenReturn(shopAdmin);
        when(deliveryRepo.findByshop(shopAdmin)).thenReturn(Collections.singletonList(delivery));

        List<Delivery> result = deliveryService.getDeliveriesByShopAdminEmail(shopAdmin.getEmail());

        assertEquals(1, result.size());
        assertEquals(delivery, result.get(0));
    }

    @Test
    public void testFindDeliveryBydeliveryemail() {
        when(deliveryRepo.findByDeliveryemail(delivery.getDeliveryemail())).thenReturn(delivery);

        Delivery result = deliveryService.findDeliveryBydeliveryemail(delivery.getDeliveryemail());

        assertEquals(delivery, result);
    }

    @Test
    public void testDisableDelivery() {
        when(deliveryRepo.findById(1L)).thenReturn(Optional.of(delivery));

        deliveryService.disableDelivery(1L);

        assertFalse(delivery.isVerified());
        verify(deliveryRepo).save(delivery);
    }
}


