package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.account.AccountSummaryDTO;
import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.dtos.shoppingCart.ShoppingCartDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;

@ExtendWith(MockitoExtension.class)
public class AccountMapperTest {

    private static final Long ID = 1L;
    private static final Integer INDEXES = 0;

    @Mock
    private UserBO mockUserBO;

    @Mock
    private ShoppingCartBO mockShoppingCartBO;

    @Mock
    private ShoppingCartDTO mockShoppingCartDTO;

    @Mock
    private AddressBO mockAddressBO;

    @Mock
    private AddressDTO mockAddressDTO;

    private AccountBO accountBO;

    @Nested
    class DtoToEntity {

        @Test
        void shouldCreateAccountBOWhenAccountDTOIsPassed() {
            // Arrange
            when(mockShoppingCartBO.getId()).thenReturn(ID);
            when(mockShoppingCartDTO.getId()).thenReturn(ID);
            when(mockUserBO.getId()).thenReturn(ID);

            AccountDTO input = new AccountDTO(ID, ID, mockShoppingCartDTO, List.of(mockAddressDTO));

            // Act
            AccountBO output = AccountMapper.dtoToEntity(input, mockUserBO, mockShoppingCartBO);

            // Assert
            assertEquals(ID, output.getId());

            assertEquals(input.getShoppingCart().getId(), output.getShoppingCart().getId());

            assertEquals(input.getUserId(), output.getUser().getId());

        }

    }

    @Nested
    class EntityToDto {

        @BeforeEach
        void setup() {
            accountBO = new AccountBO(ID, mockUserBO, mockShoppingCartBO, List.of(mockAddressBO));
            when(mockUserBO.getId()).thenReturn(ID);
            when(mockShoppingCartBO.getId()).thenReturn(ID);
            when(mockShoppingCartBO.getAccount()).thenReturn(accountBO);

        }

        @Test
        void shouldCreateAccountDTOWithoutAddressWhenAccountBOIsPassed() {
            // Act
            AccountDTO output = AccountMapper.entityToDto(accountBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(ID, output.getUserId());
            assertEquals(ID, output.getShoppingCart().getId());
        }

        @Test
        void shouldCreateAccountDTOWithAddressWhenAccountBOAndAddressArePassed() {
            // Arrange
            when(mockAddressBO.getId()).thenReturn(ID);
            when(mockAddressBO.getAccount()).thenReturn(accountBO);

            // Act
            AccountDTO output = AccountMapper.entityToDto(accountBO, accountBO.getAddresses());

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(ID, output.getUserId());
            assertEquals(ID, output.getShoppingCart().getId());

            assertEquals(1, output.getAddresses().size());
            assertEquals(ID, output.getAddresses().get(INDEXES).getId());
            assertEquals(ID, output.getAddresses().get(INDEXES).getAccountId());
        }

    }

    @Nested
    class EntityToSummaryDto {

        @Test
        void shouldCreateAccountSummaryDTOWhenAccountBOIsPassed() {
            // Arrange
            when(mockUserBO.getId()).thenReturn(ID);

            accountBO = new AccountBO();
            accountBO.setId(ID);

            accountBO.setUser(mockUserBO);

            // Act
            AccountSummaryDTO output = AccountMapper.entityToSummaryDto(accountBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(ID, output.getUserId());
        }

    }

}
