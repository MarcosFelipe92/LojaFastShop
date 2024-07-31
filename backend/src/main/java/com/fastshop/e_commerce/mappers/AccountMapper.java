package com.fastshop.e_commerce.mappers;

import java.util.stream.Collectors;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
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

        entity.setAddresses(dto.getAddresses().stream()
                .map(item -> AddressMapper.dtoToEntity(item, entity))
                .collect(Collectors.toList()));

        return entity;
    }

    public static void copyAttributes(AccountBO entity, UserBO user, ShoppingCartBO shoppingCart) {
        entity.setUser(user);
        entity.setShoppingCart(shoppingCart);
    }
}
