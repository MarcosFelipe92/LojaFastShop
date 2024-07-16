package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.AccountDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountMapper {

    public static AccountBO dtoToEntity(AccountDTO dto, UserBO user, ShoppingCartBO shoppingCart) {
        AccountBO entity = new AccountBO();

        entity.setUser(user);
        entity.setShoppingCart(shoppingCart);

        return entity;
    }

    public static void copyAttributes(AccountDTO dto, AccountBO entity, UserBO user, ShoppingCartBO shoppingCart) {
        entity.setUser(user);
        entity.setShoppingCart(shoppingCart);
    }
}
