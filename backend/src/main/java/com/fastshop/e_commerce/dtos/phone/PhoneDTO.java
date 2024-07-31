package com.fastshop.e_commerce.dtos.phone;

import com.fastshop.e_commerce.models.PhoneBO;

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

    public PhoneDTO(PhoneBO entity) {
        this.id = entity.getId();
        this.number = entity.getNumber();
        this.type = entity.getType();
        this.userId = entity.getUser().getId();
    }
}