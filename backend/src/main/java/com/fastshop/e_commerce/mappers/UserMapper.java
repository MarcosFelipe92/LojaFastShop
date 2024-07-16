package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.UserDTO;
import com.fastshop.e_commerce.models.UserBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserMapper {
    public static UserBO dtoToEntity(UserDTO dto) {
        UserBO entity = new UserBO();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());

        return entity;
    }

    public static void copyAttributes(UserDTO dto, UserBO entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());

    }
}
