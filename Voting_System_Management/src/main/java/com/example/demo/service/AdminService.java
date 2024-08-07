package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

    public boolean existsByAdminId(String id) {
        return adminRepo.existsById(id);
    }

    public void saveAdmin(Admin admin) {
        adminRepo.save(admin);
    }

    public Admin authenticateAdmin(String id, String password) {
        Admin admin = adminRepo.findById(id).orElse(null);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }
}
