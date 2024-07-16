package com.fastshop.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshop.e_commerce.models.ItemCartBO;

public interface ItemCartRepository extends JpaRepository<ItemCartBO, Long> {

}
