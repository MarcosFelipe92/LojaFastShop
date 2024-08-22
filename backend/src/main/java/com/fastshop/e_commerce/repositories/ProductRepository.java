package com.fastshop.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshop.e_commerce.models.ProductBO;

public interface ProductRepository extends JpaRepository<ProductBO, Long> {

}
