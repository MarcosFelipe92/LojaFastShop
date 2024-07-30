package com.fastshop.e_commerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshop.e_commerce.models.RoleBO;

public interface RoleRepository extends JpaRepository<RoleBO, Long> {

    Optional<RoleBO> findByName(String name);

}
