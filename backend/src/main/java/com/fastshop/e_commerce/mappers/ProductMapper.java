package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.product.ProductDTO;
import com.fastshop.e_commerce.models.ProductBO;

public class ProductMapper {
    public static ProductBO dtoToEntity(ProductDTO dto) {
        ProductBO entity = new ProductBO();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        if (dto.getImage() != null) {
            entity.setImage(dto.getImage());
        }

        return entity;
    }

    public static ProductDTO entityToDto(ProductBO entity) {
        Long id = entity.getId();
        String name = entity.getName();
        String description = entity.getDescription();
        Double price = entity.getPrice();
        String image = entity.getImage() != null ? entity.getImage() : null;

        return new ProductDTO(id, name, description, price, image);
    }

    public static void copyAttributes(ProductBO entity, ProductDTO dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());

        if (dto.getImage() != null) {
            entity.setImage(dto.getImage());
        }
    }
}
