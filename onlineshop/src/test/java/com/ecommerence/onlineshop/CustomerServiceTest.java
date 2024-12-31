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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerence.onlineshop.model.customer;
import com.ecommerence.onlineshop.repository.CustomerRepository;
import com.ecommerence.onlineshop.service.CustomerService;

class CustomerServiceTest {

    @Mock
    private CustomerRepository cusRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    private customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new customer(); // Assuming customer has a no-arg constructor
        customer.setEmail("test@example.com");
        customer.setPassword("password123");
        customer.setCustomerId(1L);
    }

    @Test
    void testSaveCustomer() {
        // TC01: Save a new customer
        when(cusRepo.findByEmail(customer.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(customer.getPassword())).thenReturn("encodedPassword");
        when(cusRepo.save(customer)).thenReturn(customer);

        boolean result = customerService.saveCustomer(customer);

        assertTrue(result);
        verify(cusRepo, times(1)).save(customer);
    }

    @Test
    void testSaveCustomerWithExistingEmail() {
        // TC02: Save a customer with existing email
        when(cusRepo.findByEmail(customer.getEmail())).thenReturn(customer);

        boolean result = customerService.saveCustomer(customer);

        assertFalse(result);
        verify(cusRepo, never()).save(customer);
    }

    @Test
    void testCheckRegisterValid() {
        // TC03: Check registration with valid credentials
        when(cusRepo.findByEmail(customer.getEmail())).thenReturn(customer);

        String result = customerService.checkRegister(customer.getEmail(), customer.getPassword());

        assertEquals("redirect:/customer/login", result);
    }

    @Test
    void testCheckRegisterInvalid() {
        // TC04: Check registration with invalid credentials
        when(cusRepo.findByEmail(customer.getEmail())).thenReturn(null);

        String result = customerService.checkRegister(customer.getEmail(), customer.getPassword());

        assertEquals("signup", result);
    }

    @Test
    void testCheckLoginValid() {
        // TC05: Check login with valid credentials
        when(cusRepo.findByEmail(customer.getEmail())).thenReturn(customer);
        when(passwordEncoder.matches(customer.getPassword(), customer.getPassword())).thenReturn(true);

        String result = customerService.checklogin(customer.getEmail(), customer.getPassword());

        assertEquals("redirect:/mainsys/homepage", result);
    }

    @Test
    void testCheckLoginInvalid() {
        // TC06: Check login with invalid credentials
        when(cusRepo.findByEmail(customer.getEmail())).thenReturn(customer);
        when(passwordEncoder.matches(customer.getPassword(), customer.getPassword())).thenReturn(false);

        String result = customerService.checklogin(customer.getEmail(), "wrongPassword");

        assertEquals("customerLoginPage", result);
    }

    @Test
    void testGetCustomerById() {
        // TC07: Get customer by ID
        when(cusRepo.findById(1L)).thenReturn(Optional.of(customer));

        customer result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals(customer, result);
    }

    @Test
    void testGetCustomerByEmail() {
        // TC08: Get customer by email
        when(cusRepo.findByEmail(customer.getEmail())).thenReturn(customer);

        customer result = customerService.getCustomerByEmail(customer.getEmail());

        assertNotNull(result);
        assertEquals(customer, result);
    }

    @Test
    void testGetAllCustomerList() {
        // TC09: Get all customers
        when(cusRepo.findAll()).thenReturn(Arrays.asList(customer));

        List<customer> result = customerService.getAllCustomerList();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
    }
}
