package com.fastshop.e_commerce.services.shoppingCart;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.auth.AuthService;
import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.dtos.product.ProductDTO;
import com.fastshop.e_commerce.dtos.shoppingCart.ShoppingCartDTO;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.mappers.ItemCartMapper;
import com.fastshop.e_commerce.mappers.ShoppingCartMapper;
import com.fastshop.e_commerce.models.ItemCartBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.repositories.ShoppingCartRepository;
import com.fastshop.e_commerce.services.account.AccountService;
import com.fastshop.e_commerce.services.itemCart.ItemCartService;
import com.fastshop.e_commerce.services.product.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository repository;
    private final AccountService accountService;
    private final AuthService authService;
    private final ItemCartService itemCartService;
    private final ProductService productService;

    @Transactional
    public ShoppingCartDTO findById(Long id, JwtAuthenticationToken token) {
        ShoppingCartBO shoppingCart = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shopping Cart not found"));
        return ShoppingCartMapper.entityToDto(shoppingCart, shoppingCart.getItems());
    }

    @Transactional
    public void addItemToCart(Long accountId, ItemCartDTO dto, JwtAuthenticationToken token) {
        AccountDTO account = accountService.findById(accountId, token);

        if (authService.validateUserPermission(token, account.getUserId())) {

            ProductDTO product = productService.findById(dto.getProduct().getId());

            if (product == null) {
                throw new NotFoundException("Produto não encontrado");
            }

            ShoppingCartBO shoppingCart = repository.findById(account.getShoppingCart().getId())
                    .orElseThrow(() -> new NotFoundException("Carrinho de compras não encontrado"));

            ItemCartBO itemCart = ItemCartMapper.dtoToEntity(dto, shoppingCart);
            shoppingCart.getItems().add(itemCart);

            repository.save(shoppingCart);
        } else {
            throw new AccessDeniedException("Você não tem permissão para adicionar itens a este carrinho.");
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
