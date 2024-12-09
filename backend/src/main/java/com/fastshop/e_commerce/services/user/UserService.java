package com.fastshop.e_commerce.services.user;

import java.util.List;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.dtos.user.UserSummaryDTO;

public interface UserService {
    List<UserSummaryDTO> findAll();

    UserDTO findById(Long id, JwtAuthenticationToken token);

    UserDTO findByEmail(String email);

    UserDTO create(UserDTO dto);

    UserDTO update(UserDTO dto, Long id, JwtAuthenticationToken token);

    void delete(Long id, JwtAuthenticationToken token);
}
