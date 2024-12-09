package com.fastshop.e_commerce.services.address;

import java.util.List;

import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.models.AccountBO;

public interface AddressService {
    List<AddressDTO> findAll();

    AddressDTO findById(Long id);

    AddressDTO create(AddressDTO dto, AccountBO account);

    void delete(Long id);
}
