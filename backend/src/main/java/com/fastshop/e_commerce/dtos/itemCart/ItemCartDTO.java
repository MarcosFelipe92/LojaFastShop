package com.fastshop.e_commerce.dtos.itemCart;

import com.fastshop.e_commerce.dtos.product.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCartDTO {
    private Long id;
    private ProductDTO product;
    private Long shoppingCartId;
}
