package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.models.ItemCartBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemCartMapper {
    public static ItemCartBO dtoToEntity(ItemCartDTO dto, ShoppingCartBO shoppingCartBO) {
        ItemCartBO entity = new ItemCartBO();

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setShoppingCart(shoppingCartBO);
        return entity;
    }

}
