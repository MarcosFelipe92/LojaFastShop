package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.models.ItemCartBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;

@ExtendWith(MockitoExtension.class)
public class ItemCartMapperTest {

    private static final Long ID = 1L;
    private static final String NAME = "Produto A";
    private static final String DESCRIPTION = "Descrição A";
    private static final Double PRICE = 20.0;

    @Mock
    private ShoppingCartBO mockShoppingCartBO;

    @Nested
    class DtoToEntity {

        @Test
        void shouldCreateItemCartBOWhenItemCartDTOIsPassed() {
            // Arrange
            when(mockShoppingCartBO.getId()).thenReturn(ID);

            ItemCartDTO input = new ItemCartDTO(ID, NAME, DESCRIPTION, PRICE, ID);

            // Act
            ItemCartBO output = ItemCartMapper.dtoToEntity(input, mockShoppingCartBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(DESCRIPTION, output.getDescription());
            assertEquals(PRICE, output.getPrice());

            assertEquals(input.getShoppingCartId(), output.getShoppingCart().getId());

        }

    }

    @Nested
    class EntityToDto {

        @Test
        void shouldCreateAddressDTOWhenAddressBOIsPassed() {
            // Arrange
            ItemCartBO input = new ItemCartBO(ID, NAME, DESCRIPTION, PRICE, mockShoppingCartBO);

            // Act
            ItemCartDTO output = ItemCartMapper.entityToDto(input);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(DESCRIPTION, output.getDescription());
            assertEquals(PRICE, output.getPrice());

            assertEquals(input.getShoppingCart().getId(), output.getShoppingCartId());

        }

    }
}
