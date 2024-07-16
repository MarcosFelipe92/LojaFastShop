package com.fastshop.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshop.e_commerce.models.AccountBO;

public interface AccountRepository extends JpaRepository<AccountBO, Long> {

}
