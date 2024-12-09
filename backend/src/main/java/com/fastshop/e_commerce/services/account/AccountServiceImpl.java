package com.fastshop.e_commerce.services.account;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.auth.AuthService;
import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.account.AccountSummaryDTO;
import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.mappers.AccountMapper;
import com.fastshop.e_commerce.mappers.AddressMapper;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;
import com.fastshop.e_commerce.repositories.AccountRepository;
import com.fastshop.e_commerce.services.address.AddressService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AddressService addressService;
    private final AuthService authService;

    public List<AccountSummaryDTO> findAll() {
        return repository.findAll().stream().map(x -> new AccountSummaryDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public AccountDTO findById(Long id, JwtAuthenticationToken token) {
        AccountBO account = repository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
        if (authService.validateUserPermission(token, account.getUser().getId())) {
            return AccountMapper.entityToDto(account, account.getAddresses());
        } else {
            throw new AccessDeniedException(
                    "You are not allowed to modify to an account that does not you.");
        }
    }

    @Transactional
    public AccountDTO create(AccountDTO dto, UserDTO user) {
        AccountBO entity = new AccountBO();

        entity = repository.save(entity);
        return AccountMapper.entityToDto(entity);
    }

    @Transactional
    public void addAddressToAccount(Long accountId, AddressDTO addressDTO, JwtAuthenticationToken token) {
        AccountBO account = repository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (authService.validateUserPermission(token, account.getUser().getId())) {
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
        AccountBO account = repository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (authService.validateUserPermission(token, account.getUser().getId())) {
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
}
