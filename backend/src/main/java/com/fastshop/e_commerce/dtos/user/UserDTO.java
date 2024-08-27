package com.fastshop.e_commerce.dtos.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.dtos.role.RoleDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
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
