package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Nested
    class DtoToEntity {
        @Test
        void shouldCreateUserBOWhenUserBOIsPassed() {
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

            assertEquals(input.getPhones().size(), output.getPhones().size());
            assertEquals(phoneDTO.getId(), output.getPhones().get(0).getId());
            assertEquals(phoneDTO.getUserId(), output.getPhones().get(0).getUser().getId());

            assertEquals(input.getPhones().size(), output.getPhones().size());
            assertEquals(NAME_ROLE, output.getRoles().iterator().next().getName());
        }
    }

    @Nested
    class EntityToDto {
        private UserBO userBO;
        private AccountBO accountBO;
        private ShoppingCartBO shoppingCartBO;
        private PhoneBO phoneBO;
        private RoleBO roleBO;

        @BeforeEach
        void setup() {
            shoppingCartBO = new ShoppingCartBO();
            accountBO = new AccountBO();
            userBO = new UserBO();
            phoneBO = new PhoneBO();
            roleBO = new RoleBO();

            shoppingCartBO.setId(ID);
            shoppingCartBO.setAccount(accountBO);

            accountBO.setId(ID);
            accountBO.setShoppingCart(shoppingCartBO);
            accountBO.setUser(userBO);

            userBO.setId(ID);
            userBO.setName(NAME);
            userBO.setEmail(EMAIL);
            userBO.setPassword(PASSWORD);
            userBO.setAccount(accountBO);

            phoneBO.setId(ID);
            phoneBO.setType(TYPE_PHONE);
            phoneBO.setNumber(NUMBER);
            phoneBO.setUser(userBO);

            roleBO.setId(ID);
            roleBO.setName(NAME_ROLE);
        }

        @Test
        void shouldCreateUserDTOWhenUserBOIsPassedWithoutRolesOrPhones() {
            // Act
            UserDTO output = UserMapper.entityToDto(userBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
            assertEquals(PASSWORD, output.getPassword());
            assertEquals(userBO.getAccount().getId(), output.getAccount().getId());
        }

        @Test
        void shouldCreateUserDTOWithRolesWhenUserBOAndRolesArePassed() {
            // Arrange
            userBO.setRoles(Set.of(roleBO));

            // Act
            UserDTO output = UserMapper.entityToDto(userBO, userBO.getRoles());

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
            assertEquals(PASSWORD, output.getPassword());
            assertEquals(userBO.getAccount().getId(), output.getAccount().getId());

            assertEquals(userBO.getRoles().size(), output.getRoles().size());
            assertEquals(userBO.getRoles().iterator().next().getId(), output.getRoles().iterator().next().getId());
        }

        @Test
        void shouldCreateUserDTOWithPhonesAndRolesWhenUserBOAndPhonesAndRolesArePassed() {
            // Arrange
            userBO.setPhones(List.of(phoneBO));
            userBO.setRoles(Set.of(roleBO));

            // Act
            UserDTO output = UserMapper.entityToDto(userBO, userBO.getRoles(), userBO.getPhones());

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
            assertEquals(PASSWORD, output.getPassword());
            assertEquals(userBO.getAccount().getId(), output.getAccount().getId());

            assertEquals(userBO.getPhones().size(), output.getPhones().size());
            assertEquals(userBO.getPhones().get(0).getId(), output.getPhones().get(0).getId());

            assertEquals(userBO.getRoles().size(), output.getRoles().size());
            assertEquals(userBO.getRoles().iterator().next().getId(), output.getRoles().iterator().next().getId());
        }

        @Test
        void shouldHandleEmptyPhonesAndRolesWhenUserBOIsPassed() {
            // Arrange
            userBO.setPhones(Collections.emptyList());
            userBO.setRoles(Collections.emptySet());

            // Act
            UserDTO output = UserMapper.entityToDto(userBO, userBO.getRoles(), userBO.getPhones());

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
            assertEquals(PASSWORD, output.getPassword());
            assertEquals(userBO.getAccount().getId(), output.getAccount().getId());

            assertEquals(0, output.getPhones().size());
            assertEquals(0, output.getRoles().size());
        }
    }

    @Nested
    class EntityToSummaryDto {

        @Test
        void shouldCreateUserSummaryDTOWhenUserBOIsPassed() {
            // Arrange
            UserBO userBO = new UserBO();
            userBO.setId(ID);
            userBO.setName(NAME);
            userBO.setEmail(EMAIL);

            // Act
            UserSummaryDTO output = UserMapper.entityToSummaryDto(userBO);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME, output.getName());
            assertEquals(EMAIL, output.getEmail());
        }
    }
}
