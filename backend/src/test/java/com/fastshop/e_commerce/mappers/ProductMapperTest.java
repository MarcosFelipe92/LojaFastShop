package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshop.e_commerce.dtos.product.ProductDTO;
import com.fastshop.e_commerce.models.ProductBO;

@ExtendWith(MockitoExtension.class)
public class ProductMapperTest {

    private static final Long ID = 1L;
    private static final String NAME = "Produto A";
    private static final String DESCRIPTION = "Descrição A";
    private static final Double PRICE = 20.0;

    @Mock
    private ProductBO mockProductBO;

    @Nested
    class DtoToEntity {

        @Test
        void shouldCreateProductBOWhenProductDTOIsPassed() {
            // Arrange
            ProductDTO input = new ProductDTO(ID, NAME, DESCRIPTION, PRICE);

            // Act
            ProductBO output = ProductMapper.dtoToEntity(input);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(DESCRIPTION, output.getDescription());
            assertEquals(PRICE, output.getPrice());

        }

    }

    @Nested
    class EntityToDto {

        @Test
        void shouldCreateProductDTOWhenProductBOIsPassed() {
            // Arrange
            ProductBO input = new ProductBO(ID, NAME, DESCRIPTION, PRICE);

            // Act
            ProductDTO output = ProductMapper.entityToDto(input);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(DESCRIPTION, output.getDescription());
            assertEquals(PRICE, output.getPrice());

        }

    }
}
