package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.models.PhoneBO;
import com.fastshop.e_commerce.models.UserBO;

@ExtendWith(MockitoExtension.class)
public class PhoneMapperTest {

    private static final Long ID = 1L;
    private static final String TYPE_PHONE = "celular";
    private static final String NUMBER = "988888888";

    @Mock
    private UserBO mockUserBO;

    @Nested
    class DtoToEntity {

        @Test
        void shouldCreatePhoneBOWhenPhoneDTOIsPassed() {
            // Arrange
            when(mockUserBO.getId()).thenReturn(ID);

            PhoneDTO input = new PhoneDTO(ID, NUMBER, TYPE_PHONE, ID);

            // Act
            PhoneBO output = PhoneMapper.dtoToEntity(input, mockUserBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NUMBER, output.getNumber());
            assertEquals(TYPE_PHONE, output.getType());
            assertEquals(input.getUserId(), output.getUser().getId());
        }

    }

    @Nested
    class EntityToDto {

        @Test
        void shouldCreatePhoneDTOWhenPhoneBOIsPassed() {
            // Arrange
            when(mockUserBO.getId()).thenReturn(ID);

            PhoneBO input = new PhoneBO(ID, NUMBER, TYPE_PHONE, mockUserBO);

            // Act
            PhoneDTO output = PhoneMapper.entityToDto(input);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NUMBER, output.getNumber());
            assertEquals(TYPE_PHONE, output.getType());
            assertEquals(input.getUser().getId(), output.getUserId());
        }

    }
}
