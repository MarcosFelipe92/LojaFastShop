package com.fastshop.e_commerce.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public ProductDTO create(ProductDTO dto, JwtAuthenticationToken token, MultipartFile image) throws IOException {
        if (authService.validateUserPermission(token)) {
            String imageBase64 = null;

            if (image != null && !image.isEmpty()) {
                imageBase64 = toBase64(image);
            }

            ProductBO product = ProductMapper.dtoToEntity(dto);
            product.setImage(imageBase64);
            product = repository.save(product);
            return ProductMapper.entityToDto(product);
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to add products");
        }
    }

    public ProductDTO update(Long id, ProductDTO dto, MultipartFile image, JwtAuthenticationToken token)
            throws IOException {
        ProductBO product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        if (authService.validateUserPermission(token)) {
            String imageBase64 = null;

            if (image != null && !image.isEmpty()) {
                imageBase64 = toBase64(image);
            }
            ProductMapper.copyAttributes(product, dto);
            product.setImage(imageBase64);
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

    public String toBase64(MultipartFile image) throws IOException {
        byte[] imageBytes = image.getBytes();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return base64Image;
    }
}
