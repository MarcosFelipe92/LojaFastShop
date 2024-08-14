package com.fastshop.e_commerce.mappers;

import com.fastshop.e_commerce.dtos.address.AddressDTO;
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

    public static AddressDTO entityToDto(AddressBO entity) {
        Long id = entity.getId();
        String zipCode = entity.getZipCode();
        String street = entity.getStreet();
        String number = entity.getNumber();
        String complement = entity.getComplement();
        String neighborhood = entity.getNeighborhood();
        String city = entity.getCity();
        String state = entity.getState();
        String country = entity.getCountry();
        Long accountId = entity.getAccount().getId();

        return new AddressDTO(id, zipCode, street, number, complement, neighborhood, city, state, country, accountId);
    }

}
