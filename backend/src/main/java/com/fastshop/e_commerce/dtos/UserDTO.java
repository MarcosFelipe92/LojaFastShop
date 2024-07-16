package com.fastshop.e_commerce.dtos;

import com.fastshop.e_commerce.models.UserBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;

    private AccountDTO account;

    public UserDTO(UserBO entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.account = new AccountDTO(entity.getAccount());
    }
}
