package com.fastshop.e_commerce.services.itemCart;

import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.repositories.ItemCartRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemCartServiceImpl implements ItemCartService {

    private final ItemCartRepository repository;

    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Item not found");
        });

        repository.deleteById(id);
    }
}
