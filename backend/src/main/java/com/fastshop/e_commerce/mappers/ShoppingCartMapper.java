package com.fastshop.e_commerce.mappers;

import java.util.stream.Collectors;

import com.fastshop.e_commerce.dtos.ShoppingCartDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ShoppingCartMapper {

    public static ShoppingCartBO dtoToEntity(ShoppingCartDTO dto, AccountBO account) {
        ShoppingCartBO entity = new ShoppingCartBO();
        System.out.println(dto.getId());

        entity.setAccount(account);
        entity.setItems(dto.getItems().stream()
                .map(item -> ItemCartMapper.dtoToEntity(item, entity))
                .collect(Collectors.toList()));

        return entity;
    }

    public static void copyAttributes(ShoppingCartDTO dto, ShoppingCartBO entity, AccountBO account) {
        entity.setId(dto.getId());
        entity.setAccount(account);
        entity.setItems(dto.getItems().stream()
                .map(item -> ItemCartMapper.dtoToEntity(item, entity))
                .collect(Collectors.toList()));

    }
}
