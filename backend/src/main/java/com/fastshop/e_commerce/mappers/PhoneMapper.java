package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.models.PhoneBO;
import com.fastshop.e_commerce.models.UserBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PhoneMapper {

    public static PhoneBO dtoToEntity(PhoneDTO dto, UserBO user) {
        PhoneBO entity = new PhoneBO();

        entity.setId(dto.getId());
        entity.setNumber(dto.getNumber());
        entity.setType(dto.getType());
        entity.setUser(user);

        return entity;
    }

    public static PhoneDTO entityToDto(PhoneBO entity) {
        Long id = entity.getId();
        String number = entity.getNumber();
        String type = entity.getType();
        Long userId = entity.getUser().getId();

        return new PhoneDTO(id, number, type, userId);
    }

}
