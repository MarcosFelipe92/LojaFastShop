package com.fastshop.e_commerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.auth.AuthService;
import com.fastshop.e_commerce.dtos.product.ProductDTO;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.mappers.ProductMapper;
import com.fastshop.e_commerce.models.ProductBO;
import com.fastshop.e_commerce.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository repository;
    private final AuthService authService;

    public List<ProductDTO> findAll() {
        return repository.findAll().stream().map(ProductMapper::entityToDto).collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        ProductBO product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        return ProductMapper.entityToDto(product);
    }

    public ProductDTO create(ProductDTO dto, JwtAuthenticationToken token) {
        if (authService.validateUserPermission(token)) {
            ProductBO product = ProductMapper.dtoToEntity(dto);
            product = repository.save(product);
            return ProductMapper.entityToDto(product);
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to add products");
        }
    }

    public ProductDTO update(Long id, ProductDTO dto, JwtAuthenticationToken token) {
        ProductBO product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        if (authService.validateUserPermission(token)) {
            ProductMapper.copyAttributes(product, dto);
            product = repository.save(product);
            return ProductMapper.entityToDto(product);
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to modify products");
        }
    }

    public void delete(Long id, JwtAuthenticationToken token) {
        repository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        if (authService.validateUserPermission(token)) {
            repository.deleteById(id);
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to delete products");
        }
    }
}
