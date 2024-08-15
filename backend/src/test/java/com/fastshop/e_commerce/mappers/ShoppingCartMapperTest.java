package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.dtos.shoppingCart.ShoppingCartDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.ItemCartBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartMapperTest {

    private static final Long ID = 1L;

    @Mock
    private AccountBO mockAccountBO;

    @Mock
    private ItemCartBO mockItemCartBO;

    @Mock
    private ItemCartDTO mockItemCartDTO;

    @Nested
    class DtoToEntity {

        @Test
        void shouldCreateShoppingCartBOWhenShoppingCartDTOIsPassed() {
            // Arrange
            when(mockAccountBO.getId()).thenReturn(ID);
            when(mockItemCartDTO.getId()).thenReturn(ID);

            ShoppingCartDTO input = new ShoppingCartDTO(ID, ID, List.of(mockItemCartDTO));

            // Act
            ShoppingCartBO output = ShoppingCartMapper.dtoToEntity(input, mockAccountBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(input.getAccountId(), output.getAccount().getId());

            assertEquals(input.getItems().size(), output.getItems().size());
            assertEquals(input.getItems().get(0).getId(), output.getItems().get(0).getId());

        }

    }

    @Nested
    class EntityToDto {

        @Test
        void shouldCreateShoppingCartDTOWithoutItemsWhenShoppingCartBOIsPassed() {
            // Arrange
            when(mockAccountBO.getId()).thenReturn(ID);

            ShoppingCartBO input = new ShoppingCartBO(ID, mockAccountBO, List.of(mockItemCartBO));

            // Act
            ShoppingCartDTO output = ShoppingCartMapper.entityToDto(input);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(input.getAccount().getId(), output.getAccountId());

        }

        @Test
        void shouldCreateShoppingCartDTOWithItemsWhenShoppingCartBOAndItemsArePassed() {
            // Arrange
            when(mockAccountBO.getId()).thenReturn(ID);
            when(mockItemCartBO.getId()).thenReturn(ID);

            ShoppingCartBO input = new ShoppingCartBO(ID, mockAccountBO, List.of(mockItemCartBO));

            when(mockItemCartBO.getShoppingCart()).thenReturn(input);

            // Act
            ShoppingCartDTO output = ShoppingCartMapper.entityToDto(input, input.getItems());

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(input.getAccount().getId(), output.getAccountId());

            assertEquals(input.getItems().size(), output.getItems().size());
            assertEquals(input.getItems().get(0).getId(), output.getItems().get(0).getId());

        }

    }
}
