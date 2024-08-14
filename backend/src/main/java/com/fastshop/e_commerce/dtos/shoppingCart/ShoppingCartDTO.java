package com.fastshop.e_commerce.dtos.shoppingCart;

import java.util.ArrayList;
import java.util.List;

import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShoppingCartDTO {
    private Long id;
    private Long accountId;
    private List<ItemCartDTO> items = new ArrayList<>();

    public ShoppingCartDTO(Long id, Long accountId) {
        this.id = id;
        this.accountId = accountId;
    }

    public ShoppingCartDTO(Long id, Long accountId, List<ItemCartDTO> items) {
        this(id, accountId);
        this.items.addAll(items);
    }
}
