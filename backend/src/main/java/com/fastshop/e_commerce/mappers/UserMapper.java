package com.fastshop.e_commerce.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.dtos.role.RoleDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.dtos.user.UserSummaryDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.PhoneBO;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.UserBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserMapper {
    public static UserBO dtoToEntity(UserDTO dto) {
        UserBO entity = new UserBO();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());

        entity.setPhones(dto.getPhones().stream()
                .map(item -> PhoneMapper.dtoToEntity(item, entity))
                .collect(Collectors.toList()));

        entity.setRoles(dto.getRoles().stream()
                .map(RoleMapper::dtoToEntity)
                .collect(Collectors.toSet()));

        return entity;
    }

    public static UserSummaryDTO entityToSummaryDto(UserBO user) {
        Long id = user.getId();
        String name = user.getName();
        String email = user.getEmail();

        return new UserSummaryDTO(id, name, email);
    }

    public static UserDTO entityToDto(UserBO user) {
        Long id = user.getId();
        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();
        AccountDTO account = AccountMapper.entityToDto(user.getAccount());

        return new UserDTO(id, name, email, password, account);
    }

    public static UserDTO entityToDto(UserBO user, Set<RoleBO> roles) {
        Long id = user.getId();
        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();
        AccountDTO account = AccountMapper.entityToDto(user.getAccount());
        Set<RoleDTO> rolesToAdd = roles.stream().map(RoleMapper::entityToDto).collect(Collectors.toSet());

        return new UserDTO(id, name, email, password, account, rolesToAdd);
    }

    public static UserDTO entityToDto(UserBO user, Set<RoleBO> roles, List<PhoneBO> phones) {
        Long id = user.getId();
        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();
        AccountDTO account = AccountMapper.entityToDto(user.getAccount());
        Set<RoleDTO> rolesToAdd = roles.stream().map(RoleMapper::entityToDto).collect(Collectors.toSet());
        List<PhoneDTO> phonesToAdd = phones.stream().map(PhoneMapper::entityToDto).collect(Collectors.toList());

        return new UserDTO(id, name, email, password, account, rolesToAdd, phonesToAdd);
    }

    public static void copyAttributes(UserDTO dto, UserBO entity, AccountBO account) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setAccount(account);

        List<PhoneBO> updatedPhones = dto.getPhones().stream()
                .map(item -> PhoneMapper.dtoToEntity(item, entity))
                .collect(Collectors.toList());

        entity.getPhones().clear();
        entity.getPhones().addAll(updatedPhones);

    }
}
