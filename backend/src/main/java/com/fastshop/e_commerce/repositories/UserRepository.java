package com.fastshop.e_commerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshop.e_commerce.models.UserBO;

public interface UserRepository extends JpaRepository<UserBO, Long> {
    Optional<UserBO> findByEmail(String email);
}
