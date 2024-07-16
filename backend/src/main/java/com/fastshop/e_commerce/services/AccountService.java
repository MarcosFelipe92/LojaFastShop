package com.fastshop.e_commerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.AccountDTO;
import com.fastshop.e_commerce.dtos.UserDTO;
import com.fastshop.e_commerce.exceptions.service.ResourceNotFoundException;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.repositories.AccountRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public List<AccountDTO> findAll() {
        return repository.findAll().stream().map(x -> new AccountDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public AccountDTO findById(Long id) {
        AccountBO entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new AccountDTO(entity);
    }

    @Transactional
    public AccountDTO insert(AccountDTO dto, UserDTO user) {
        AccountBO entity = new AccountBO();

        entity = repository.save(entity);
        return new AccountDTO(entity);
    }

    // @Transactional
    // public AccountDTO update(AccountDTO dto, Long id) {
    // try {
    // AccountBO account = repository.getReferenceById(id);
    // ShoppingCartBO shoppingCartBO = ShoppingCartMapper
    // .dtoToEntity(shoppingCartService.findById(dto.getShoppingCart().getId()),
    // account);
    // UserBO user = UserMapper.dtoToEntity(userService.findById(dto.getUserId()));
    // account.setShoppingCart(shoppingCartBO);
    // account.setUser(user);
    // return new AccountDTO(repository.save(account));
    // } catch (EntityNotFoundException e) {
    // throw new ResourceNotFoundException("id " + id + " not found");
    // }
    // }
}
