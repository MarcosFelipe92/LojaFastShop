package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.dtos.role.RoleDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.dtos.user.UserSummaryDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.PhoneBO;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    private static final Long ID = 1L;
    private static final String NAME = "John Doe";
    private static final String EMAIL = "johndoe@example.com";
    private static final String PASSWORD = "password123";
    private static final String TYPE_PHONE = "celular";
    private static final String NUMBER = "988888888";
    private static final String NAME_ROLE = "BASIC";

    @Mock
    private AccountBO accountBO;

    @Mock
    private ShoppingCartBO shoppingCartBO;

    @Mock
    private PhoneBO phoneBO;

    @Mock
    private RoleBO roleBO;

    @Mock
    private UserBO userBO;

    @Nested
    class DtoToEntity {

        @Test
        void shouldCreateUserBOWhenUserDTOIsPassed() {
            // Arrange
            PhoneDTO phoneDTO = new PhoneDTO(ID, NUMBER, TYPE_PHONE, ID);
            RoleDTO roleDTO = new RoleDTO(ID, NAME_ROLE);

            UserDTO input = new UserDTO(ID, NAME, EMAIL, PASSWORD, null,
                    Set.of(roleDTO), List.of(phoneDTO));

            // Act
            UserBO output = UserMapper.dtoToEntity(input);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
            assertEquals(PASSWORD, output.getPassword());

            assertEquals(1, output.getPhones().size());
            assertEquals(phoneDTO.getId(), output.getPhones().get(0).getId());
            assertEquals(NAME_ROLE, output.getRoles().iterator().next().getName());
        }
    }

    @Nested
    class EntityToDto {

        @BeforeEach
        void setup() {
            when(accountBO.getId()).thenReturn(ID);

            when(shoppingCartBO.getId()).thenReturn(ID);
            when(shoppingCartBO.getAccount()).thenReturn(accountBO);

            when(userBO.getId()).thenReturn(ID);
            when(userBO.getName()).thenReturn(NAME);
            when(userBO.getEmail()).thenReturn(EMAIL);
            when(userBO.getPassword()).thenReturn(PASSWORD);
            when(userBO.getAccount()).thenReturn(accountBO);

            when(accountBO.getUser()).thenReturn(userBO);
            when(accountBO.getShoppingCart()).thenReturn(shoppingCartBO);

        }

        @Test
        void shouldCreateUserDTOWithoutRolesOrPhonesWhenUserBOIsPassed() {
            // Act
            UserDTO output = UserMapper.entityToDto(userBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
            assertEquals(PASSWORD, output.getPassword());
            assertEquals(ID, output.getAccount().getId());
        }

        @Test
        void shouldCreateUserDTOWithRolesWhenUserBOAndRolesArePassed() {
            // Arrange
            when(roleBO.getId()).thenReturn(ID);
            when(roleBO.getName()).thenReturn(NAME_ROLE);

            when(userBO.getRoles()).thenReturn(Set.of(roleBO));

            // Act
            UserDTO output = UserMapper.entityToDto(userBO, userBO.getRoles());

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
            assertEquals(PASSWORD, output.getPassword());
            assertEquals(ID, output.getAccount().getId());

            assertEquals(1, output.getRoles().size());
            assertEquals(ID, output.getRoles().iterator().next().getId());
        }

        @Test
        void shouldCreateUserDTOWithPhonesAndRolesWhenUserBOAndPhonesAndRolesArePassed() {
            // Arrange
            when(phoneBO.getId()).thenReturn(ID);
            when(phoneBO.getType()).thenReturn(TYPE_PHONE);
            when(phoneBO.getNumber()).thenReturn(NUMBER);
            when(phoneBO.getUser()).thenReturn(userBO);

            when(roleBO.getId()).thenReturn(ID);
            when(roleBO.getName()).thenReturn(NAME_ROLE);

            when(userBO.getPhones()).thenReturn(List.of(phoneBO));
            when(userBO.getRoles()).thenReturn(Set.of(roleBO));

            // Act
            UserDTO output = UserMapper.entityToDto(userBO, userBO.getRoles(), userBO.getPhones());

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
            assertEquals(PASSWORD, output.getPassword());
            assertEquals(ID, output.getAccount().getId());

            assertEquals(1, output.getPhones().size());
            assertEquals(ID, output.getPhones().get(0).getId());

            assertEquals(1, output.getRoles().size());
            assertEquals(ID, output.getRoles().iterator().next().getId());
        }

        @Test
        void shouldHandleEmptyPhonesAndRolesWhenUserBOIsPassed() {
            // Arrange
            when(userBO.getPhones()).thenReturn(Collections.emptyList());
            when(userBO.getRoles()).thenReturn(Collections.emptySet());

            // Act
            UserDTO output = UserMapper.entityToDto(userBO, userBO.getRoles(), userBO.getPhones());

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
            assertEquals(PASSWORD, output.getPassword());
            assertEquals(ID, output.getAccount().getId());

            assertEquals(0, output.getPhones().size());
            assertEquals(0, output.getRoles().size());
        }
    }

    @Nested
    class EntityToSummaryDto {

        @Test
        void shouldCreateUserSummaryDTOWhenUserBOIsPassed() {
            // Arrange
            when(userBO.getId()).thenReturn(ID);
            when(userBO.getName()).thenReturn(NAME);
            when(userBO.getEmail()).thenReturn(EMAIL);

            // Act
            UserSummaryDTO output = UserMapper.entityToSummaryDto(userBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
        }
    }
}
