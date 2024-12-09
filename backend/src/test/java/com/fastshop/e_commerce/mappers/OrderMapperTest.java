package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshop.e_commerce.dtos.itemOrder.ItemOrderDTO;
import com.fastshop.e_commerce.dtos.order.OrderDTO;
import com.fastshop.e_commerce.dtos.product.ProductDTO;
import com.fastshop.e_commerce.enuns.OrderStatus;
import com.fastshop.e_commerce.enuns.PaymentMethod;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;
import com.fastshop.e_commerce.models.ItemOrderBO;
import com.fastshop.e_commerce.models.OrderBO;
import com.fastshop.e_commerce.models.ProductBO;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTest {

    private static final Long ID = 1L;
    private static final Double TOTAL_PRICE = 100.0;
    private static final Double DISCOUNT = 10.0;
    private static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    private static final OrderStatus STATUS = OrderStatus.PENDING;
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2024, Month.DECEMBER, 9, 10, 0, 0, 0);
    private static final LocalDateTime UPDATED_AT = LocalDateTime.of(2024, Month.DECEMBER, 9, 12, 0, 0, 0);
    private static final Long ACCOUNT_ID = 1L;
    private static final Long DELIVERY_ADDRESS_ID = 1L;

    @Mock
    private ProductBO mockProductBO;

    @Mock
    private ProductDTO mockProductDTO;

    @Mock
    private AccountBO mockAccountBO;

    @Mock
    private AddressBO mockAddressBO;

    @Mock
    private ItemOrderDTO mockItemOrderDTO;

    @Mock
    private ItemOrderBO mockItemOrderBO;

    @Nested
    class DtoToEntity {
        @Test
        void shouldCreateOrderBOWhenOrderDTOIsPassed() {
            // Arrange
            when(mockAccountBO.getId()).thenReturn(ID);
            when(mockAddressBO.getId()).thenReturn(ID);
            when(mockItemOrderDTO.getId()).thenReturn(ID);
            when(mockItemOrderDTO.getProduct()).thenReturn(mockProductDTO);

            OrderDTO input = new OrderDTO(ID, TOTAL_PRICE, DISCOUNT, PAYMENT_METHOD, STATUS, CREATED_AT, UPDATED_AT,
                    ACCOUNT_ID, DELIVERY_ADDRESS_ID, List.of(mockItemOrderDTO));

            // Act
            OrderBO output = OrderMapper.dtoToEntity(input, mockAccountBO, mockAddressBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(input.getTotalPrice(), output.getTotalPrice());
            assertEquals(input.getDiscount(), output.getDiscount());
            assertEquals(input.getPaymentMethod(), output.getPaymentMethod());
            assertEquals(input.getStatus(), output.getStatus());
            assertEquals(input.getCreatedAt(), output.getCreatedAt());
            assertEquals(input.getUpdatedAt(), output.getUpdatedAt());

            assertEquals(input.getAccountId(), output.getAccount().getId());
            assertEquals(input.getDeliveryAddressId(), output.getDeliveryAddress().getId());

            assertEquals(input.getItems().size(), output.getItems().size());
            assertEquals(input.getItems().get(0).getId(), output.getItems().get(0).getId());
        }
    }

    @Nested
    class EntityToDto {

        @Test
        void shouldCreateOrderDTOWithoutItemsWhenOrderBOIsPassed() {
            // Arrange
            when(mockAccountBO.getId()).thenReturn(ID);

            OrderBO input = new OrderBO(ID, TOTAL_PRICE, DISCOUNT, PAYMENT_METHOD, STATUS, CREATED_AT, UPDATED_AT,
                    mockAccountBO, mockAddressBO, List.of(mockItemOrderBO));

            // Act
            OrderDTO output = OrderMapper.entityToDto(input);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(input.getAccount().getId(), output.getAccountId());

        }

        @Test
        void shouldCreateOrderDTOWithItemsWhenOrderBOAndItemsArePassed() {
            // Arrange
            when(mockAccountBO.getId()).thenReturn(ID);
            when(mockItemOrderBO.getId()).thenReturn(ID);
            when(mockItemOrderBO.getProduct()).thenReturn(mockProductBO);
            when(mockProductBO.getId()).thenReturn(ID);

            OrderBO input = new OrderBO(ID, TOTAL_PRICE, DISCOUNT, PAYMENT_METHOD, STATUS, CREATED_AT, UPDATED_AT,
                    mockAccountBO, mockAddressBO, List.of(mockItemOrderBO));

            when(mockItemOrderBO.getOrder()).thenReturn(input);

            // Act
            OrderDTO output = OrderMapper.entityToDto(input, input.getItems());

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(input.getTotalPrice(), output.getTotalPrice());
            assertEquals(input.getDiscount(), output.getDiscount());
            assertEquals(input.getPaymentMethod(), output.getPaymentMethod());
            assertEquals(input.getStatus(), output.getStatus());
            assertEquals(input.getCreatedAt(), output.getCreatedAt());
            assertEquals(input.getUpdatedAt(), output.getUpdatedAt());

            assertEquals(input.getAccount().getId(), output.getAccountId());
            assertEquals(input.getDeliveryAddress().getId(), output.getDeliveryAddressId());

            assertEquals(input.getItems().size(), output.getItems().size());
            assertEquals(input.getItems().get(0).getId(), output.getItems().get(0).getId());

        }

    }
}
