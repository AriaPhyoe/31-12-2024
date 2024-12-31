package com.ecommerence.onlineshop;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ecommerence.onlineshop.model.Admin;
import com.ecommerence.onlineshop.repository.AdminRepository;
import com.ecommerence.onlineshop.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    private Admin admin;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = new Admin();
        admin.setAdminId(1L);
        admin.setEmail("admin@example.com");
        admin.setPassword("password");
    }

    @Test
    public void testGetAllData() {
        when(adminRepository.findAll()).thenReturn(Collections.singletonList(admin));
        
        List<Admin> result = adminService.getAllData();
        assertEquals(1, result.size());
        assertEquals(admin, result.get(0));
    }

    @Test
    public void testGetAdminIdEdit() {
        when(adminRepository.findByAdminId(1L)).thenReturn(admin);
        
        Admin result = adminService.getAdminIdEdit(1L);
        assertEquals(admin, result);
    }

    @Test
    public void testGetAdminIdEdit_NonExistent() {
        when(adminRepository.findByAdminId(2L)).thenReturn(null);
        
        Admin result = adminService.getAdminIdEdit(2L);
        assertNull(result);
    }

    @Test
    public void testSaveAdmin() {
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(null);
        
        boolean result = adminService.saveAdmin(admin);
        assertTrue(result);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    public void testSaveAdmin_EmailExists() {
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(admin);
        
        boolean result = adminService.saveAdmin(admin);
        assertFalse(result);
        verify(adminRepository, times(0)).save(admin);
    }

    @Test
    public void testCheckLogin_ValidCredentials() {
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(admin);
        when(adminRepository.findByEmailAndPassword(admin.getEmail(), admin.getPassword())).thenReturn(admin);
        
        String result = adminService.checklogin(admin.getEmail(), admin.getPassword());
        assertEquals("redirect:/admin/main", result);
    }

    @Test
    public void testCheckLogin_InvalidPassword() {
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(admin);
        when(adminRepository.findByEmailAndPassword(admin.getEmail(), "wrongPassword")).thenReturn(null);
        
        String result = adminService.checklogin(admin.getEmail(), "wrongPassword");
        assertEquals("adminLogin", result);
    }

    @Test
    public void testCheckLogin_NonExistentEmail() {
        when(adminRepository.findByEmail("nonExistent@example.com")).thenReturn(null);
        
        String result = adminService.checklogin("nonExistent@example.com", admin.getPassword());
        assertEquals("adminLogin", result);
    }

    @Test
    public void testUpdateAdmin() {
        adminService.updateAdmin(1L, admin);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    public void testGetAdminByEmail() {
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(admin);
        
        Admin result = adminService.getAdminByEmail(admin.getEmail());
        assertEquals(admin, result);
    }
}

