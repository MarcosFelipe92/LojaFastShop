package com.fastshop.e_commerce.services.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    public List<RoleBO> findAll() {
        return repository.findAll();
    }

    public RoleBO findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new NotFoundException("Role not found"));
    }
}
