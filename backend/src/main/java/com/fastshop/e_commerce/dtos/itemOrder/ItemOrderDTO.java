package com.fastshop.e_commerce.dtos.itemOrder;

import com.fastshop.e_commerce.dtos.product.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrderDTO {
    private Long id;
    private ProductDTO product;
    private Integer quantity;
    private Long orderId;
}
