package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.PhoneDTO;
import com.fastshop.e_commerce.models.PhoneBO;
import com.fastshop.e_commerce.models.UserBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PhoneMapper {

    public static PhoneBO dtoToEntity(PhoneDTO dto, UserBO user) {
        PhoneBO entity = new PhoneBO();

        entity.setNumber(dto.getNumber());
        entity.setType(dto.getType());
        entity.setUser(user);

        return entity;
    }

    public static void copyAttributes(PhoneDTO dto, PhoneBO entity, UserBO user) {
        entity.setId(dto.getId());
        entity.setNumber(dto.getNumber());
        entity.setType(dto.getType());
        entity.setUser(user);
    }
}
