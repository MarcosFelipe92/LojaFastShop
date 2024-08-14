package com.fastshop.e_commerce.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.account.AccountSummaryDTO;
import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.dtos.shoppingCart.ShoppingCartDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountMapper {

    public static AccountBO dtoToEntity(AccountDTO dto, UserBO user, ShoppingCartBO shoppingCart) {
        AccountBO entity = new AccountBO();

        entity.setId(dto.getId());
        entity.setUser(user);
        entity.setShoppingCart(shoppingCart);

        entity.setAddresses(dto.getAddresses().stream()
                .map(item -> AddressMapper.dtoToEntity(item, entity))
                .collect(Collectors.toList()));

        return entity;
    }

    public static AccountSummaryDTO entityToSummaryDto(AccountBO entity) {
        Long id = entity.getId();
        Long userId = entity.getUser().getId();

        return new AccountSummaryDTO(id, userId);
    }

    public static AccountDTO entityToDto(AccountBO entity) {
        Long id = entity.getId();
        Long userId = entity.getUser().getId();
        ShoppingCartDTO shoppingCart = ShoppingCartMapper.entityToDto(entity.getShoppingCart());

        return new AccountDTO(id, userId, shoppingCart);
    }

    public static AccountDTO entityToDto(AccountBO entity, List<AddressBO> addresses) {
        Long id = entity.getId();
        Long userId = entity.getUser().getId();
        ShoppingCartDTO shoppingCart = ShoppingCartMapper.entityToDto(entity.getShoppingCart());
        List<AddressDTO> addressesToAdd = addresses.stream().map(AddressMapper::entityToDto)
                .collect(Collectors.toList());

        return new AccountDTO(id, userId, shoppingCart, addressesToAdd);
    }

    public static void copyAttributes(AccountBO entity, UserBO user, ShoppingCartBO shoppingCart) {
        entity.setUser(user);
        entity.setShoppingCart(shoppingCart);
    }
}
