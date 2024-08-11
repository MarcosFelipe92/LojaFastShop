package com.fastshop.e_commerce.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.dtos.user.UserSummaryDTO;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.exceptions.service.InvalidEmailException;
import com.fastshop.e_commerce.mappers.UserMapper;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;
import com.fastshop.e_commerce.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public List<UserSummaryDTO> findAll() {
        return repository.findAll().stream().map(x -> new UserSummaryDTO(x)).collect(Collectors.toList());
    }

    public UserDTO findById(Long id, JwtAuthenticationToken token) {
        if (validateUserPermission(token, id)) {
            UserBO entity = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
            return new UserDTO(entity, entity.getPhones(), entity.getRoles());
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to modify to an user that does not you.");
        }
    }

    public UserDTO findByEmail(String email) {
        UserBO entity = repository.findByEmail(email);
        return new UserDTO(entity, entity.getRoles());
    }

    @Transactional
    public UserDTO create(UserDTO dto) {
        UserBO userExists = repository.findByEmail(dto.getEmail());
        if (userExists != null) {
            throw new InvalidEmailException("Email already registered");
        }
        ;

        RoleBO basicRole = roleService.findByName(RoleBO.getBasicRole());

        UserBO user = UserMapper.dtoToEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(basicRole));

        AccountBO account = new AccountBO();
        ShoppingCartBO shoppingCart = new ShoppingCartBO();
        account.setShoppingCart(shoppingCart);
        shoppingCart.setAccount(account);

        user.setAccount(account);
        account.setUser(user);

        user = repository.save(user);
        return new UserDTO(user, user.getPhones(), user.getRoles());
    }

    @Transactional
    public UserDTO update(UserDTO dto, Long id, JwtAuthenticationToken token) {
        if (validateUserPermission(token, id)) {
            UserBO userUpdated = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
            AccountBO account = userUpdated.getAccount();
            UserMapper.copyAttributes(dto, userUpdated, account);
            userUpdated.setPassword(passwordEncoder.encode(dto.getPassword()));
            userUpdated = repository.save(userUpdated);
            return new UserDTO(userUpdated);
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to modify to an user that does not you.");
        }
    }

    public void delete(Long id, JwtAuthenticationToken token) {
        if (validateUserPermission(token, id)) {
            repository.deleteById(id);
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to delete to an user that does not you.");
        }
    }

    private boolean validateUserPermission(JwtAuthenticationToken token, Long dbUserId) {
        UserBO requestUser = repository.findById(Long.parseLong(token.getName())).get();
        UserBO userFromDb = repository.findById(dbUserId).orElseThrow(() -> new NotFoundException("User not found"));
        boolean isAdmin = requestUser.hasRole(RoleBO.getAdminRole());

        if (isAdmin || requestUser.getId().equals(userFromDb.getId())) {
            return true;
        }
        return false;
    }
}
