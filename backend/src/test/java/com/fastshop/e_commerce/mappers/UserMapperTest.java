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
    private AccountBO mockAccountBO;

    @Mock
    private ShoppingCartBO mockShoppingCartBO;

    @Mock
    private PhoneBO mockPhoneBO;

    @Mock
    private RoleBO mockRoleBO;

    @Mock
    private UserBO mockUserBO;

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
            when(mockAccountBO.getId()).thenReturn(ID);

            when(mockShoppingCartBO.getId()).thenReturn(ID);
            when(mockShoppingCartBO.getAccount()).thenReturn(mockAccountBO);

            when(mockUserBO.getId()).thenReturn(ID);
            when(mockUserBO.getName()).thenReturn(NAME);
            when(mockUserBO.getEmail()).thenReturn(EMAIL);
            when(mockUserBO.getPassword()).thenReturn(PASSWORD);
            when(mockUserBO.getAccount()).thenReturn(mockAccountBO);

            when(mockAccountBO.getUser()).thenReturn(mockUserBO);
            when(mockAccountBO.getShoppingCart()).thenReturn(mockShoppingCartBO);

        }

        @Test
        void shouldCreateUserDTOWithoutRolesOrPhonesWhenUserBOIsPassed() {
            // Act
            UserDTO output = UserMapper.entityToDto(mockUserBO);

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
            when(mockRoleBO.getId()).thenReturn(ID);
            when(mockRoleBO.getName()).thenReturn(NAME_ROLE);

            when(mockUserBO.getRoles()).thenReturn(Set.of(mockRoleBO));

            // Act
            UserDTO output = UserMapper.entityToDto(mockUserBO, mockUserBO.getRoles());

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
            when(mockPhoneBO.getId()).thenReturn(ID);
            when(mockPhoneBO.getType()).thenReturn(TYPE_PHONE);
            when(mockPhoneBO.getNumber()).thenReturn(NUMBER);
            when(mockPhoneBO.getUser()).thenReturn(mockUserBO);

            when(mockRoleBO.getId()).thenReturn(ID);
            when(mockRoleBO.getName()).thenReturn(NAME_ROLE);

            when(mockUserBO.getPhones()).thenReturn(List.of(mockPhoneBO));
            when(mockUserBO.getRoles()).thenReturn(Set.of(mockRoleBO));

            // Act
            UserDTO output = UserMapper.entityToDto(mockUserBO, mockUserBO.getRoles(), mockUserBO.getPhones());

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
            when(mockUserBO.getPhones()).thenReturn(Collections.emptyList());
            when(mockUserBO.getRoles()).thenReturn(Collections.emptySet());

            // Act
            UserDTO output = UserMapper.entityToDto(mockUserBO, mockUserBO.getRoles(), mockUserBO.getPhones());

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
            when(mockUserBO.getId()).thenReturn(ID);
            when(mockUserBO.getName()).thenReturn(NAME);
            when(mockUserBO.getEmail()).thenReturn(EMAIL);

            // Act
            UserSummaryDTO output = UserMapper.entityToSummaryDto(mockUserBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
        }
    }
}
