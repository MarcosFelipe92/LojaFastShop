package com.fastshop.e_commerce.dtos.role;

import com.fastshop.e_commerce.models.RoleBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;

    public RoleDTO(RoleBO entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
