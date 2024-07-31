package com.fastshop.e_commerce.mappers;

import java.util.stream.Collectors;

import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.UserBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserMapper {
    public static UserBO dtoToEntity(UserDTO dto) {
        UserBO entity = new UserBO();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());

        entity.setPhones(dto.getPhones().stream()
                .map(item -> PhoneMapper.dtoToEntity(item, entity))
                .collect(Collectors.toList()));

        entity.setRoles(dto.getRoles().stream()
                .map(item -> RoleMapper.dtoToEntity(item))
                .collect(Collectors.toSet()));

        return entity;
    }

    public static void copyAttributes(UserDTO dto, UserBO entity, AccountBO account) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setAccount(account);

        entity.setPhones(dto.getPhones().stream()
                .map(item -> PhoneMapper.dtoToEntity(item, entity))
                .collect(Collectors.toList()));

    }
}
