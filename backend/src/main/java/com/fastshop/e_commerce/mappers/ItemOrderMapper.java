package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.itemOrder.ItemOrderDTO;
import com.fastshop.e_commerce.dtos.product.ProductDTO;
import com.fastshop.e_commerce.models.ItemOrderBO;
import com.fastshop.e_commerce.models.OrderBO;

public class ItemOrderMapper {
    public static ItemOrderBO dtoToEntity(ItemOrderDTO dto, OrderBO orderBO) {
        ItemOrderBO entity = new ItemOrderBO();

        entity.setId(dto.getId());
        entity.setProduct(ProductMapper.dtoToEntity(dto.getProduct()));
        entity.setOrder(orderBO);
        entity.setQuantity(dto.getQuantity());
        return entity;
    }

    public static ItemOrderDTO entityToDto(ItemOrderBO entity) {
        Long id = entity.getId();
        ProductDTO product = ProductMapper.entityToDto(entity.getProduct());
        Long orderId = entity.getOrder().getId();
        Integer quantity = entity.getQuantity();

        return new ItemOrderDTO(id, product, quantity, orderId);
    }
}