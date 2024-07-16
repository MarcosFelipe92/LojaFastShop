package com.fastshop.e_commerce.dtos;

import com.fastshop.e_commerce.models.AccountBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private Long userId;
    private ShoppingCartDTO shoppingCart;

    public AccountDTO(AccountBO entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.shoppingCart = new ShoppingCartDTO(entity.getShoppingCart());
    }
}
