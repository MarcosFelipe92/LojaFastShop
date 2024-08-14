package com.fastshop.e_commerce.dtos.phone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
    private Long id;
    private String number;
    private String type;
    private Long userId;
}