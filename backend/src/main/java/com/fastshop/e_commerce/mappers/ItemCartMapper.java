package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.dtos.product.ProductDTO;
import com.fastshop.e_commerce.models.ItemCartBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemCartMapper {
    public static ItemCartBO dtoToEntity(ItemCartDTO dto, ShoppingCartBO shoppingCartBO) {
        ItemCartBO entity = new ItemCartBO();

        entity.setId(dto.getId());
        entity.setProduct(ProductMapper.dtoToEntity(dto.getProduct()));
        entity.setShoppingCart(shoppingCartBO);
        entity.setQuantity(dto.getQuantity());
        return entity;
    }

    public static ItemCartDTO entityToDto(ItemCartBO entity) {
        Long id = entity.getId();
        ProductDTO product = ProductMapper.entityToDto(entity.getProduct());
        Long shoppingCartId = entity.getShoppingCart().getId();
        Integer quantity = entity.getQuantity();

        return new ItemCartDTO(id, product, quantity, shoppingCartId);
    }

}
