package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;

@ExtendWith(MockitoExtension.class)
public class AddressMapperTest {

    private static final Long ID = 1L;
    private static final String ZIP_CODE = "12345-678";
    private static final String STREET = "Rua Exemplo";
    private static final String NUMBER = "28";
    private static final String COMPLEMENT = "Quadra 3, Casa 4";
    private static final String NEIGHBORHOOD = "Bairro Exemplo";
    private static final String CITY = "Cidade Exemplo";
    private static final String STATE = "Estado Exemplo";
    private static final String COUNTRY = "Pais Exemplo";

    @Mock
    private AccountBO mockAccountBO;

    @Nested
    class DtoToEntity {

        @Test
        void shouldCreateAddressBOWhenAddressDTOIsPassed() {
            // Arrange
            when(mockAccountBO.getId()).thenReturn(ID);

            AddressDTO input = new AddressDTO(ID, ZIP_CODE, STREET, NUMBER, COMPLEMENT, NEIGHBORHOOD, CITY, STATE,
                    COUNTRY, ID);

            // Act
            AddressBO output = AddressMapper.dtoToEntity(input, mockAccountBO);

            // Assert
            assertEquals(ZIP_CODE, output.getZipCode());
            assertEquals(STREET, output.getStreet());
            assertEquals(NUMBER, output.getNumber());
            assertEquals(COMPLEMENT, output.getComplement());
            assertEquals(NEIGHBORHOOD, output.getNeighborhood());
            assertEquals(CITY, output.getCity());
            assertEquals(STATE, output.getState());
            assertEquals(COUNTRY, output.getCountry());

            assertEquals(ID, output.getAccount().getId());
        }

    }

    @Nested
    class EntityToDto {

        @Test
        void shouldCreateAddressDTOWhenAddressBOIsPassed() {
            // Arrange
            when(mockAccountBO.getId()).thenReturn(ID);

            AddressBO input = new AddressBO(ID, ZIP_CODE, STREET, NUMBER, COMPLEMENT, NEIGHBORHOOD, CITY, STATE,
                    COUNTRY, mockAccountBO);

            // Act
            AddressDTO output = AddressMapper.entityToDto(input);

            // Assert
            assertEquals(ZIP_CODE, output.getZipCode());
            assertEquals(STREET, output.getStreet());
            assertEquals(NUMBER, output.getNumber());
            assertEquals(COMPLEMENT, output.getComplement());
            assertEquals(NEIGHBORHOOD, output.getNeighborhood());
            assertEquals(CITY, output.getCity());
            assertEquals(STATE, output.getState());
            assertEquals(COUNTRY, output.getCountry());

            assertEquals(ID, output.getAccountId());
        }

    }

}
