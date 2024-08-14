package com.fastshop.e_commerce.dtos.account;

import java.util.ArrayList;
import java.util.List;

import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.dtos.shoppingCart.ShoppingCartDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private Long userId;

    private ShoppingCartDTO shoppingCart;
    private List<AddressDTO> addresses = new ArrayList<>();

    public AccountDTO(Long id, Long userId, ShoppingCartDTO shoppingCart) {
        this.id = id;
        this.userId = userId;
        this.shoppingCart = shoppingCart;
    }

    public AccountDTO(Long id, Long userId, ShoppingCartDTO shoppingCart, List<AddressDTO> addresses) {
        this.id = id;
        this.userId = userId;
        this.shoppingCart = shoppingCart;
        this.addresses.addAll(addresses);
    }

}
