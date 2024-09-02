package com.fastshop.e_commerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.mappers.AddressMapper;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;
import com.fastshop.e_commerce.repositories.AddressRepository;

import jakarta.transaction.Transactional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository repository;

    public List<AddressDTO> findAll() {
        return repository.findAll().stream().map(AddressMapper::entityToDto).collect(Collectors.toList());
    }

    @Transactional
    public AddressDTO findById(Long id) {
        AddressBO address = repository.findById(id).orElseThrow(() -> new NotFoundException("Address not found"));
        return AddressMapper.entityToDto(address);
    }

    @Transactional
    public AddressDTO create(AddressDTO dto, AccountBO account) {
        AddressBO address = AddressMapper.dtoToEntity(dto, account);

        address = repository.save(address);
        return AddressMapper.entityToDto(address);
    }

    @Transactional
    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Address not found");
        });

        repository.deleteById(id);
    }
}