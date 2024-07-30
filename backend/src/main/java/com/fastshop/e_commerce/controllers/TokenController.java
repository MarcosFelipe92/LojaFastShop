package com.fastshop.e_commerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.e_commerce.dtos.login.LoginRequestDTO;
import com.fastshop.e_commerce.dtos.login.LoginResponseDTO;
import com.fastshop.e_commerce.services.TokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        System.out.println("Chegou aqui 2");
        LoginResponseDTO loginResponse = tokenService.login(request);
        return ResponseEntity.ok(loginResponse);
    }
}
