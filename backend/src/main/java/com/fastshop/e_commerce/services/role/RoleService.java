package com.fastshop.e_commerce.services.role;

import java.util.List;

import com.fastshop.e_commerce.models.RoleBO;

public interface RoleService {
    List<RoleBO> findAll();

    RoleBO findByName(String name);
}
