package com.fastshop.e_commerce.services.account;

import java.util.List;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.account.AccountSummaryDTO;
import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;

public interface AccountService {
    List<AccountSummaryDTO> findAll();

    AccountDTO findById(Long id, JwtAuthenticationToken token);

    AccountDTO create(AccountDTO dto, UserDTO user);

    void addAddressToAccount(Long accountId, AddressDTO addressDTO, JwtAuthenticationToken token);

    void removeAddressToAccount(Long accountId, Long addressId, JwtAuthenticationToken token);
}
