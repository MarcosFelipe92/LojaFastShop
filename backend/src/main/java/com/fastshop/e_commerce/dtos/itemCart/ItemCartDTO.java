package com.fastshop.e_commerce.dtos.itemCart;

import com.fastshop.e_commerce.models.ItemCartBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCartDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long shoppingCartId;

    public ItemCartDTO(ItemCartBO entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.shoppingCartId = entity.getShoppingCart().getId();
    }
}
