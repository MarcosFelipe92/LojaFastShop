package com.fastshop.e_commerce.dtos.address;

import com.fastshop.e_commerce.models.AddressBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;
    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private Long accountId;

    public AddressDTO(AddressBO entity) {
        this.id = entity.getId();
        this.zipCode = entity.getZipCode();
        this.street = entity.getStreet();
        this.number = entity.getNumber();
        this.complement = entity.getComplement();
        this.neighborhood = entity.getNeighborhood();
        this.city = entity.getCity();
        this.state = entity.getState();
        this.country = entity.getCountry();
        this.accountId = entity.getAccount().getId();
    }
}
