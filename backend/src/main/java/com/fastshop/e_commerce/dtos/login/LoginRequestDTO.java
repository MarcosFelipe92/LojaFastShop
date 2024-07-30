package com.fastshop.e_commerce.dtos.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequestDTO {
    private String email;
    private String password;
}
