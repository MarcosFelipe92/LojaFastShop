package com.fastshop.e_commerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.mappers.AddressMapper;
import com.fastshop.e_commerce.mappers.UserMapper;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.UserBO;
import com.fastshop.e_commerce.repositories.AccountRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository repository;
    private final UserService userService;
    private final AddressService addressService;

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
    public void addAddressToAccount(Long accountId, AddressDTO addressDTO, JwtAuthenticationToken token) {
        UserBO user = UserMapper.dtoToEntity(userService.findById(Long.parseLong(token.getName())));
        boolean isAdmin = user.hasRole(RoleBO.Values.ADMIN.name());

        AccountBO account = repository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (isAdmin || account.getUser().getId().equals(user.getId())) {
            AddressBO address = AddressMapper.dtoToEntity(addressDTO, account);
            account.getAddresses().add(address);
            repository.save(account);
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to add an address to an account that does not belong to you.");
        }
    }

    @Transactional
    public void removeAddressToAccount(Long accountId, Long addressId, JwtAuthenticationToken token) {
        UserBO user = UserMapper.dtoToEntity(userService.findById(Long.parseLong(token.getName())));
        boolean isAdmin = user.hasRole(RoleBO.Values.ADMIN.name());

        AccountBO account = repository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (isAdmin || account.getUser().getId().equals(user.getId())) {
            AddressBO addressToRemove = null;
            for (AddressBO address : account.getAddresses()) {
                if (address.getId().equals(addressId)) {
                    addressToRemove = address;
                    break;
                }
            }

            if (addressToRemove != null) {
                account.getAddresses().remove(addressToRemove);
                addressService.delete(addressToRemove.getId());
                repository.save(account);
            } else {
                throw new NotFoundException("Address not found");
            }
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to remove an address to an account that does not belong to you.");
        }
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
