package com.fastshop.e_commerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.mappers.AddressMapper;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;
import com.fastshop.e_commerce.repositories.AccountRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private AddressService addressService;

    public List<AccountDTO> findAll() {
        return repository.findAll().stream().map(x -> new AccountDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public AccountDTO findById(Long id) {
        AccountBO entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Entity not found"));
        return new AccountDTO(entity, entity.getAddresses());
    }

    @Transactional
    public AccountDTO insert(AccountDTO dto, UserDTO user) {
        AccountBO entity = new AccountBO();

        entity = repository.save(entity);
        return new AccountDTO(entity);
    }

    @Transactional
    public List<AddressDTO> addAddressToAccount(Long accountId, AddressDTO addressDTO) {
        AccountBO account = repository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        AddressDTO savedAddressDTO = addressService.insert(addressDTO, account);
        AddressBO address = AddressMapper.dtoToEntity(savedAddressDTO, account);

        account.getAddresses().add(address);

        repository.save(account);
        return new AccountDTO(account).getAddresses();

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
