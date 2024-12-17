package com.fastshop.e_commerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.e_commerce.dtos.order.OrderDTO;
import com.fastshop.e_commerce.services.order.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll(JwtAuthenticationToken token) {
        List<OrderDTO> list = service.findAll(token);
        return ResponseEntity.ok().body(list);
    }

}
