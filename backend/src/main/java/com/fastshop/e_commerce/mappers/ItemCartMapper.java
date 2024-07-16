package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.ItemCartDTO;
import com.fastshop.e_commerce.models.ItemCartBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemCartMapper {
    public static ItemCartBO dtoToEntity(ItemCartDTO dto) {
        ItemCartBO entity = new ItemCartBO();

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        return entity;
    }

}
