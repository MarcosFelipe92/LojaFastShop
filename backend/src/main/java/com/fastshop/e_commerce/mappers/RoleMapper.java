package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.role.RoleDTO;
import com.fastshop.e_commerce.models.RoleBO;

public class RoleMapper {
    public static RoleBO dtoToEntity(RoleDTO dto) {
        RoleBO entity = new RoleBO();

        entity.setId(dto.getId());
        entity.setName(dto.getName());

        return entity;
    }

    public static RoleDTO entityToDto(RoleBO entity) {
        Long id = entity.getId();
        String name = entity.getName();

        return new RoleDTO(id, name);
    }
}
