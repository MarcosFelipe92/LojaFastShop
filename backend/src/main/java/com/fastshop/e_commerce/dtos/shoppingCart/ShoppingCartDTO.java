package com.fastshop.e_commerce.dtos.shoppingCart;

import java.util.ArrayList;
import java.util.List;

import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.models.ItemCartBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {
    private Long id;
    private Long accountId;
    private List<ItemCartDTO> items = new ArrayList<>();

    public ShoppingCartDTO(ShoppingCartBO entity) {
        id = entity.getId();
        accountId = entity.getAccount().getId();
    }

    public ShoppingCartDTO(ShoppingCartBO entity, List<ItemCartBO> items) {
        this(entity);
        items.forEach(i -> this.items.add(new ItemCartDTO(i)));
    }
}
