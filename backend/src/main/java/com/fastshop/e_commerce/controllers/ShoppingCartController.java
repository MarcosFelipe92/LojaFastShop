package com.fastshop.e_commerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.dtos.shoppingCart.ShoppingCartDTO;
import com.fastshop.e_commerce.services.ShoppingCartService;

@RestController
@RequestMapping("accounts/{accountId}/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService service;

    @GetMapping
    public ResponseEntity<ShoppingCartDTO> findById(
            @PathVariable Long accountId, JwtAuthenticationToken token) {
        ShoppingCartDTO shoppingCart = service.findById(accountId, token);
        return ResponseEntity.ok().body(shoppingCart);
    }

    @PostMapping
    public ResponseEntity<String> addItemToShoppingCart(
            @PathVariable Long accountId,
            @RequestBody ItemCartDTO itemCartDTO, JwtAuthenticationToken token) {
        service.addItemToCart(accountId, itemCartDTO, token);

        return ResponseEntity.status(201).body("Item added successfully");
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> addItemToShoppingCart(
            @PathVariable Long accountId,
            @PathVariable Long itemId,
            JwtAuthenticationToken token) {
        service.removeItemToCart(accountId, itemId, token);
        return ResponseEntity.status(200).body("Item removed successfully");
    }
}
