package com.fastshop.e_commerce.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.dtos.user.UserSummaryDTO;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.exceptions.service.DatabaseException;
import com.fastshop.e_commerce.exceptions.service.InvalidEmailException;
import com.fastshop.e_commerce.mappers.UserMapper;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;
import com.fastshop.e_commerce.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
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

    public UserDTO findById(Long id) {
        UserBO entity = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserDTO(entity, entity.getPhones(), entity.getRoles());
    }

    public Optional<UserBO> findByEmail(String email) {
        Optional<UserBO> entity = repository.findByEmail(email);
        return entity;
    }

    @Transactional
    public UserDTO register(UserDTO dto) {
        repository.findByEmail(dto.getEmail()).ifPresent(user -> {
            throw new InvalidEmailException("Email already registered");
        });

        RoleBO basicRole = roleService.findByName(RoleBO.Values.BASIC.name());

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
    public UserDTO update(UserDTO dto, Long id) {
        try {
            UserBO user = repository.findById(id).orElseThrow(() -> new NotFoundException("Entity not found"));

            AccountBO account = user.getAccount();

            UserMapper.copyAttributes(dto, user, account);
            user = repository.save(user);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("id " + id + " not found");
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
