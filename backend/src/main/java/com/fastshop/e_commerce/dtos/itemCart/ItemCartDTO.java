package com.fastshop.e_commerce.dtos.itemCart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCartDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long shoppingCartId;
}
