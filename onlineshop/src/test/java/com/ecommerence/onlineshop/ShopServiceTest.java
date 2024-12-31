package com.ecommerence.onlineshop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ecommerence.onlineshop.model.shop;
import com.ecommerence.onlineshop.repository.ShopRepository;
import com.ecommerence.onlineshop.service.ShopService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopServiceTest {

    @InjectMocks
    private ShopService shopService;

    @Mock
    private ShopRepository shopRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveShop() {
        shop newShop = new shop();
        newShop.setEmail("shop@example.com");
        newShop.setPassword("password");

        when(shopRepo.findByEmail(newShop.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(newShop.getPassword())).thenReturn("encodedPassword");
        
        boolean result = shopService.saveShop(newShop);
        assertTrue(result);
        verify(shopRepo, times(1)).save(newShop);
    }

    @Test
    public void testSaveShopWithExistingEmail() {
        shop existingShop = new shop();
        existingShop.setEmail("existing@example.com");

        when(shopRepo.findByEmail(existingShop.getEmail())).thenReturn(existingShop);
        
        boolean result = shopService.saveShop(existingShop);
        assertFalse(result);
        verify(shopRepo, never()).save(existingShop);
    }

    @Test
    public void testCheckRegisterNewShop() {
        String email = "newshop@example.com";
        String password = "password";
        shop existingShop = null;

        when(shopRepo.findByEmail(email)).thenReturn(existingShop);

        String result = shopService.checkRegister(email, password);
        assertEquals("adminregister", result);
    }

    @Test
    public void testCheckRegisterExistingShop() {
        String email = "existingshop@example.com";
        String password = "password";
        shop existingShop = new shop();
        existingShop.setEmail(email);
        
        when(shopRepo.findByEmail(email)).thenReturn(existingShop);
        
        String result = shopService.checkRegister(email, password);
        assertEquals("home", result);
    }

    @Test
    public void testCheckLoginValidCredentials() {
        String email = "shop@example.com";
        String password = "password";
        shop existingShop = new shop();
        existingShop.setEmail(email);
        existingShop.setPassword("encodedPassword");
        existingShop.setVerified(true);

        when(shopRepo.findByEmail(email)).thenReturn(existingShop);
        when(passwordEncoder.matches(password, existingShop.getPassword())).thenReturn(true);
        
        String result = shopService.checkLogin(email, password);
        assertEquals("shopAdminHome", result);
    }

    @Test
    public void testGetShopByEmail() {
        String email = "shop@example.com";
        shop existingShop = new shop();
        existingShop.setEmail(email);

        when(shopRepo.findByEmail(email)).thenReturn(existingShop);
        
        shop result = shopService.getCustomerByEmail(email);
        assertEquals(existingShop, result);
    }

    @Test
    public void testFindShopById() {
        Long shopId = 1L;
        shop existingShop = new shop();
        existingShop.setShopId(shopId);

        when(shopRepo.findById(shopId)).thenReturn(Optional.of(existingShop));
        
        shop result = shopService.findShopByshopId(shopId);
        assertEquals(existingShop, result);
    }

    @Test
    public void testDisableShop() {
        Long shopId = 1L;
        shop existingShop = new shop();
        existingShop.setShopId(shopId);
        existingShop.setVerified(true);

        when(shopRepo.findById(shopId)).thenReturn(Optional.of(existingShop));
        
        shopService.disableShop(shopId);
        assertFalse(existingShop.isVerified());
        verify(shopRepo, times(1)).save(existingShop);
    }

    @Test
    public void testGetAllShopRequests() {
        List<shop> shopList = new ArrayList<>();
        when(shopRepo.findAll()).thenReturn(shopList);
        
        List<shop> result = shopService.getAllShopRequests();
        assertEquals(shopList, result);
    }

    @Test
    public void testGetShopByNonExistentId() {
        Long shopId = 999L;
        when(shopRepo.findById(shopId)).thenReturn(Optional.empty());
        
        Optional<shop> result = shopService.getShopById(shopId);
        assertFalse(result.isPresent());
    }
}
