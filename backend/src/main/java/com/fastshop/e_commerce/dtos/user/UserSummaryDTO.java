package com.fastshop.e_commerce.dtos.user;

import com.fastshop.e_commerce.models.UserBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryDTO {
    private Long id;
    private String name;
    private String email;
    private String password;

    public UserSummaryDTO(UserBO entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
    }
}
