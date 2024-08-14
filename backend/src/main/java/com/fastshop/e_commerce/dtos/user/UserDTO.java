package com.fastshop.e_commerce.dtos.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.dtos.role.RoleDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;

    private AccountDTO account;
    private List<PhoneDTO> phones = new ArrayList<>();
    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(Long id, String name, String email, String password, AccountDTO account) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.account = account;
    }

    public UserDTO(Long id, String name, String email, String password, AccountDTO account, Set<RoleDTO> roles) {
        this(id, name, email, password, account);
        this.roles.addAll(roles);
    }

    public UserDTO(Long id, String name, String email, String password, AccountDTO account, Set<RoleDTO> roles,
            List<PhoneDTO> phones) {
        this(id, name, email, password, account, roles);
        this.phones.addAll(phones);
    }

}
