package com.fastshop.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshop.e_commerce.models.AddressBO;

public interface AddressRepository extends JpaRepository<AddressBO, Long> {

}
