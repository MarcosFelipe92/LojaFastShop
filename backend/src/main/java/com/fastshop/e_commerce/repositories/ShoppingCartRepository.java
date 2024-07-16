package com.fastshop.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshop.e_commerce.models.ShoppingCartBO;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartBO, Long> {

}
