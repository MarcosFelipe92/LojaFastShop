package com.fastshop.e_commerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.services.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService service;

    @GetMapping()
    public ResponseEntity<List<AccountDTO>> findAll() {
        List<AccountDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable Long id) {
        AccountDTO entity = service.findById(id);
        return ResponseEntity.ok().body(entity);
    }

    @PostMapping("/{id}")
    public ResponseEntity<List<AddressDTO>> addAddressToAccount(@PathVariable Long id,
            @RequestBody AddressDTO addressDto) {
        List<AddressDTO> list = service.addAddressToAccount(id, addressDto);
        return ResponseEntity.ok().body(list);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<AccountDTO> update(@RequestBody AccountDTO dto,
    // @PathVariable Long id) {
    // AccountDTO obj = service.update(dto, id);
    // return ResponseEntity.ok().body(obj);
    // }

}
