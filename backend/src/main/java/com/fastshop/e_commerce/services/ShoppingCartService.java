package com.fastshop.e_commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.shoppingCart.ShoppingCartDTO;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.mappers.ShoppingCartMapper;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.repositories.ShoppingCartRepository;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository repository;

    public ShoppingCartDTO findById(Long id) {
        ShoppingCartBO entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shopping Cart not found"));
        return ShoppingCartMapper.entityToDto(entity);
    }
}
