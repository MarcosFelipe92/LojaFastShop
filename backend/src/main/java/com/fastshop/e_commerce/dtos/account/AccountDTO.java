package com.fastshop.e_commerce.dtos.account;

import java.util.ArrayList;
import java.util.List;

import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.dtos.shoppingCart.ShoppingCartDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;

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
    private List<AddressDTO> addresses = new ArrayList<>();

    public AccountDTO(AccountBO entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.shoppingCart = new ShoppingCartDTO(entity.getShoppingCart());
    }

    public AccountDTO(AccountBO entity, List<AddressBO> addresses) {
        this(entity);
        addresses.forEach(i -> this.addresses.add(new AddressDTO(i)));
    }
}
