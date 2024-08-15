package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.models.ItemCartBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemCartMapper {
    public static ItemCartBO dtoToEntity(ItemCartDTO dto, ShoppingCartBO shoppingCartBO) {
        ItemCartBO entity = new ItemCartBO();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setShoppingCart(shoppingCartBO);
        return entity;
    }

    public static ItemCartDTO entityToDto(ItemCartBO entity) {
        Long id = entity.getId();
        String name = entity.getName();
        String description = entity.getDescription();
        Double price = entity.getPrice();
        Long shoppingCartId = entity.getShoppingCart().getId();

        return new ItemCartDTO(id, name, description, price, shoppingCartId);
    }

}
