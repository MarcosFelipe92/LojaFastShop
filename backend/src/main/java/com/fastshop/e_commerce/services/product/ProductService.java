package com.fastshop.e_commerce.services.product;

import java.io.IOException;
import java.util.List;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import com.fastshop.e_commerce.dtos.product.ProductDTO;

public interface ProductService {
    List<ProductDTO> findAll();

    ProductDTO findById(Long id);

    ProductDTO create(ProductDTO dto, JwtAuthenticationToken token, MultipartFile image) throws IOException;

    ProductDTO update(Long id, ProductDTO dto, MultipartFile image, JwtAuthenticationToken token) throws IOException;

    void delete(Long id, JwtAuthenticationToken token);
}
