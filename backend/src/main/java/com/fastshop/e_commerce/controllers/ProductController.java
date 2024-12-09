package com.fastshop.e_commerce.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fastshop.e_commerce.dtos.product.ProductDTO;
import com.fastshop.e_commerce.services.product.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id, JwtAuthenticationToken token) {
        ProductDTO entity = service.findById(id);
        return ResponseEntity.ok().body(entity);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestPart("dto") ProductDTO dto, JwtAuthenticationToken token,
            @RequestPart(value = "file", required = false) MultipartFile image) throws IOException {
        ProductDTO obj = service.create(dto, token, image);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestPart("dto") ProductDTO dto,
            JwtAuthenticationToken token,
            @RequestPart(value = "file", required = false) MultipartFile image) throws IOException {
        ProductDTO obj = service.update(id, dto, image, token);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id,
            JwtAuthenticationToken token) {
        service.delete(id, token);
        return ResponseEntity.noContent().build();
    }

}
