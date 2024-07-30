package com.fastshop.e_commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.exceptions.service.ResourceNotFoundException;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.repositories.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public RoleBO findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }
}
