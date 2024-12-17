package com.fastshop.e_commerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshop.e_commerce.models.OrderBO;

public interface OrderRepository extends JpaRepository<OrderBO, Long> {
    List<OrderBO> findByAccount_User_Id(Long userId);
}
