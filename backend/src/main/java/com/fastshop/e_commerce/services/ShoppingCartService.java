package com.fastshop.e_commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.ShoppingCartDTO;
import com.fastshop.e_commerce.exceptions.service.ResourceNotFoundException;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.repositories.ShoppingCartRepository;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository repository;

    public ShoppingCartDTO findById(Long id) {
        ShoppingCartBO entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ShoppingCartDTO(entity);
    }
}
