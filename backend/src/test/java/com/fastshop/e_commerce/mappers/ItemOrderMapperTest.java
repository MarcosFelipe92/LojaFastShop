package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshop.e_commerce.dtos.itemOrder.ItemOrderDTO;
import com.fastshop.e_commerce.dtos.product.ProductDTO;
import com.fastshop.e_commerce.models.ItemOrderBO;
import com.fastshop.e_commerce.models.OrderBO;
import com.fastshop.e_commerce.models.ProductBO;

@ExtendWith(MockitoExtension.class)
public class ItemOrderMapperTest {
    private static final Long ID = 1L;
    private static final String NAME = "Produto A";
    private static final String DESCRIPTION = "Descrição A";
    private static final Double PRICE = 20.0;
    private static final String IMAGE = "imagem";
    private static final Integer QUANTITY = 1;

    @Mock
    private OrderBO mockOrderBO;

    @Nested
    class DtoToEntity {
        @Test
        void shouldCreateItemOrderBOWhenItemOrderDTOIsPassed() {
            // Arrange
            when(mockOrderBO.getId()).thenReturn(ID);
            ProductDTO productDTO = new ProductDTO(ID, NAME, DESCRIPTION, PRICE, IMAGE);

            ItemOrderDTO input = new ItemOrderDTO(ID, productDTO, QUANTITY, ID);

            // Act
            ItemOrderBO output = ItemOrderMapper.dtoToEntity(input, mockOrderBO);

            // assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getProduct().getName());
            assertEquals(DESCRIPTION, output.getProduct().getDescription());
            assertEquals(PRICE, output.getProduct().getPrice());

            assertEquals(input.getOrderId(), output.getOrder().getId());
        }
    }

    @Nested
    class EntityToDto {
        @Test
        void shouldCreateItemOrderDTOWhenItemOrderBOIsPassed() {
            // Arrange
            ProductBO productBO = new ProductBO(ID, NAME, DESCRIPTION, PRICE, IMAGE);

            ItemOrderBO input = new ItemOrderBO(ID, QUANTITY, productBO, mockOrderBO);

            // Act
            ItemOrderDTO output = ItemOrderMapper.entityToDto(input);

            // assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getProduct().getName());
            assertEquals(DESCRIPTION, output.getProduct().getDescription());
            assertEquals(PRICE, output.getProduct().getPrice());

            assertEquals(input.getOrder().getId(), output.getOrderId());
        }
    }
}
