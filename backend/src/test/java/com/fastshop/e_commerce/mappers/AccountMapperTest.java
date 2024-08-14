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
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.AddressBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;

@ExtendWith(MockitoExtension.class)
public class AccountMapperTest {

    private static final Long ID = 1L;
    private static final Integer INDEXES = 0;

    @Mock
    private UserBO userBO;

    @Mock
    private ShoppingCartBO shoppingCartBO;

    @Mock
    private AddressBO addressBO;

    private AccountBO accountBO;

    @Nested
    class EntityToDto {

        @BeforeEach
        void setup() {
            accountBO = new AccountBO(ID, userBO, shoppingCartBO, List.of(addressBO));
            when(userBO.getId()).thenReturn(ID);
            when(shoppingCartBO.getId()).thenReturn(ID);
            when(shoppingCartBO.getAccount()).thenReturn(accountBO);

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
            when(addressBO.getId()).thenReturn(ID);
            when(addressBO.getAccount()).thenReturn(accountBO);

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
            accountBO = new AccountBO();
            accountBO.setId(ID);

            userBO = new UserBO();
            userBO.setId(ID);
            accountBO.setUser(userBO);

            // Act
            AccountSummaryDTO output = AccountMapper.entityToSummaryDto(accountBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(ID, output.getUserId());
        }

    }

}
