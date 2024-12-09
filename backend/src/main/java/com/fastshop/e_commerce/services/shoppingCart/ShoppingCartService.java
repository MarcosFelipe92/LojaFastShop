package com.fastshop.e_commerce.services.shoppingCart;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.dtos.shoppingCart.ShoppingCartDTO;

public interface ShoppingCartService {
    ShoppingCartDTO findById(Long id, JwtAuthenticationToken token);

    void addItemToCart(Long shoppingCartId, ItemCartDTO itemDTO, JwtAuthenticationToken token);

    void removeItemToCart(Long shoppingCartId, Long itemId, JwtAuthenticationToken token);
}
