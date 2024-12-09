package com.fastshop.e_commerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.account.AccountSummaryDTO;
import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.services.account.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService service;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<AccountSummaryDTO>> findAll() {
        List<AccountSummaryDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable Long id, JwtAuthenticationToken token) {
        AccountDTO entity = service.findById(id, token);
        return ResponseEntity.ok().body(entity);
    }

    @PostMapping("/{id}/address")
    public ResponseEntity<String> addAddressToAccount(@PathVariable Long id,
            @RequestBody AddressDTO addressDto, JwtAuthenticationToken token) {
        service.addAddressToAccount(id, addressDto, token);
        return ResponseEntity.status(201).body("Address added successfully");
    }

    @DeleteMapping("/{id}/address/{addressId}")
    public ResponseEntity<String> removeAddressToAccount(@PathVariable Long id,
            @PathVariable Long addressId, JwtAuthenticationToken token) {
        service.removeAddressToAccount(id, addressId, token);
        return ResponseEntity.ok().body("Address removed successfully");
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<AccountDTO> update(@RequestBody AccountDTO dto,
    // @PathVariable Long id) {
    // AccountDTO obj = service.update(dto, id);
    // return ResponseEntity.ok().body(obj);
    // }

}
