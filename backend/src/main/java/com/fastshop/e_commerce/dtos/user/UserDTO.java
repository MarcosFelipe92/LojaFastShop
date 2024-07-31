package com.fastshop.e_commerce.dtos.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.dtos.role.RoleDTO;
import com.fastshop.e_commerce.models.PhoneBO;
import com.fastshop.e_commerce.models.RoleBO;
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
    private List<PhoneDTO> phones = new ArrayList<>();
    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(UserBO entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.account = new AccountDTO(entity.getAccount());
    }

    public UserDTO(UserBO entity, List<PhoneBO> phones, Set<RoleBO> roles) {
        this(entity);
        phones.forEach(i -> this.phones.add(new PhoneDTO(i)));
        roles.forEach(i -> this.roles.add(new RoleDTO(i)));
    }
}
