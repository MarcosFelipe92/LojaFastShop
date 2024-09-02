package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.dtos.product.ProductDTO;
import com.fastshop.e_commerce.models.ItemCartBO;
import com.fastshop.e_commerce.models.ProductBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;

@ExtendWith(MockitoExtension.class)
public class ItemCartMapperTest {

    private static final Long ID = 1L;
    private static final String NAME = "Produto A";
    private static final String DESCRIPTION = "Descrição A";
    private static final Double PRICE = 20.0;
    private static final String IMAGE = "imagem";

    @Mock
    private ShoppingCartBO mockShoppingCartBO;

    @Nested
    class DtoToEntity {

        @Test
        void shouldCreateItemCartBOWhenItemCartDTOIsPassed() {
            // Arrange
            when(mockShoppingCartBO.getId()).thenReturn(ID);
            ProductDTO productDTO = new ProductDTO(ID, NAME, DESCRIPTION, PRICE, IMAGE);

            ItemCartDTO input = new ItemCartDTO(ID, productDTO, ID);

            // Act
            ItemCartBO output = ItemCartMapper.dtoToEntity(input, mockShoppingCartBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getProduct().getName());
            assertEquals(DESCRIPTION, output.getProduct().getDescription());
            assertEquals(PRICE, output.getProduct().getPrice());

            assertEquals(input.getShoppingCartId(), output.getShoppingCart().getId());

        }

    }

    @Nested
    class EntityToDto {

        @Test
        void shouldCreateItemCartDTOWhenItemCartBOIsPassed() {
            // Arrange

            ProductBO productBO = new ProductBO(ID, NAME, DESCRIPTION, PRICE, IMAGE);

            ItemCartBO input = new ItemCartBO(ID, productBO, mockShoppingCartBO);

            // Act
            ItemCartDTO output = ItemCartMapper.entityToDto(input);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getProduct().getName());
            assertEquals(DESCRIPTION, output.getProduct().getDescription());
            assertEquals(PRICE, output.getProduct().getPrice());

            assertEquals(input.getShoppingCart().getId(), output.getShoppingCartId());

        }

    }
}
