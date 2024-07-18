package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.AddressDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AddressMapper {

    public static AddressBO dtoToEntity(AddressDTO dto, AccountBO account) {
        AddressBO entity = new AddressBO();

        entity.setZipCode(dto.getZipCode());
        entity.setStreet(dto.getStreet());
        entity.setNumber(dto.getNumber());
        entity.setComplement(dto.getComplement());
        entity.setNeighborhood(dto.getNeighborhood());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setAccount(account);

        return entity;
    }

    public static void copyAttributes(AddressDTO dto, AddressBO entity, AccountBO account) {
        entity.setId(dto.getId());
        entity.setZipCode(dto.getZipCode());
        entity.setStreet(dto.getStreet());
        entity.setNumber(dto.getNumber());
        entity.setComplement(dto.getComplement());
        entity.setNeighborhood(dto.getNeighborhood());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setAccount(account);
    }
}
