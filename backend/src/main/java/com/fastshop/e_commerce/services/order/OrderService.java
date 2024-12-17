package com.fastshop.e_commerce.services.order;

import java.util.List;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.dtos.order.OrderDTO;

public interface OrderService {
    List<OrderDTO> findAll(JwtAuthenticationToken token);

    OrderDTO findById(Long id, JwtAuthenticationToken token);

    OrderDTO create(OrderDTO dto, AddressDTO address, JwtAuthenticationToken token);

    void delete(Long id, JwtAuthenticationToken token);
}
