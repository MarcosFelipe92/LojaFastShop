package com.fastshop.e_commerce.services;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.auth.AuthService;
import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.dtos.shoppingCart.ShoppingCartDTO;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.mappers.ItemCartMapper;
import com.fastshop.e_commerce.mappers.ShoppingCartMapper;
import com.fastshop.e_commerce.models.ItemCartBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.repositories.ShoppingCartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShoppingCartService {

    private final ShoppingCartRepository repository;
    private final AccountService accountService;
    private final AuthService authService;
    private final ItemCartService itemCartService;

    public ShoppingCartDTO findById(Long accountId, JwtAuthenticationToken token) {
        AccountDTO account = accountService.findById(accountId, token);
        ShoppingCartBO shoppingCart = repository.findById(account.getShoppingCart().getId())
                .orElseThrow(() -> new NotFoundException("ShoppingCart not found"));
        return ShoppingCartMapper.entityToDto(shoppingCart, shoppingCart.getItems());

    }

    @Transactional
    public void addItemToCart(Long accountId, ItemCartDTO dto,
            JwtAuthenticationToken token) {
        AccountDTO account = accountService.findById(accountId, token);
        if (authService.validateUserPermission(token, account.getUserId())) {
            ShoppingCartBO shoppingCart = repository.findById(account.getShoppingCart().getId())
                    .orElseThrow(() -> new NotFoundException("ShoppingCart not found"));
            ItemCartBO itemCart = ItemCartMapper.dtoToEntity(dto, shoppingCart);
            shoppingCart.getItems().add(itemCart);
            repository.save(shoppingCart);
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to remove an address to an account that does not belong to you.");
        }
    }

    @Transactional
    public void removeItemToCart(Long accountId, Long itemId,
            JwtAuthenticationToken token) {
        AccountDTO account = accountService.findById(accountId, token);
        if (authService.validateUserPermission(token, account.getUserId())) {
            ShoppingCartBO shoppingCart = repository.findById(account.getShoppingCart().getId())
                    .orElseThrow(() -> new NotFoundException("ShoppingCart not found"));
            ItemCartBO itemCartToRemove = null;

            for (ItemCartBO item : shoppingCart.getItems()) {
                if (item.getId().equals(itemId)) {
                    itemCartToRemove = item;
                    break;
                }
            }

            if (itemCartToRemove != null) {
                shoppingCart.getItems().remove(itemCartToRemove);
                itemCartService.delete(itemCartToRemove.getId());
                repository.save(shoppingCart);
            } else {
                throw new NotFoundException("Item not found");
            }
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to remove an address to an account that does not belong to you.");
        }
    }
}
