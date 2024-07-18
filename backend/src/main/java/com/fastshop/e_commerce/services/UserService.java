package com.fastshop.e_commerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.dtos.user.UserSummaryDTO;
import com.fastshop.e_commerce.exceptions.service.DatabaseException;
import com.fastshop.e_commerce.exceptions.service.ResourceNotFoundException;
import com.fastshop.e_commerce.mappers.UserMapper;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;
import com.fastshop.e_commerce.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<UserSummaryDTO> findAll() {
        return repository.findAll().stream().map(x -> new UserSummaryDTO(x)).collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        UserBO entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDTO(entity, entity.getPhones());
    }

    @Transactional
    public UserDTO register(UserDTO dto) {
        UserBO user = UserMapper.dtoToEntity(dto);

        AccountBO account = new AccountBO();
        ShoppingCartBO shoppingCart = new ShoppingCartBO();
        account.setShoppingCart(shoppingCart);
        shoppingCart.setAccount(account);

        user.setAccount(account);
        account.setUser(user);

        user = repository.save(user);
        return new UserDTO(user, user.getPhones());
    }

    @Transactional
    public UserDTO update(UserDTO dto, Long id) {
        try {
            UserBO user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

            AccountBO account = user.getAccount();

            UserMapper.copyAttributes(dto, user, account);
            user = repository.save(user);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("id " + id + " not found");
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
