package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.dtos.role.RoleDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.dtos.user.UserSummaryDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.PhoneBO;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;

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
            assertEquals(input.getId(), output.getId());
            assertEquals(input.getName(), output.getName());
            assertEquals(input.getEmail(), output.getEmail());
            assertEquals(input.getPassword(), output.getPassword());

            assertEquals(input.getPhones().size(), output.getPhones().size());
            assertEquals(phoneDTO.getNumber(), output.getPhones().get(0).getNumber());
            assertEquals(phoneDTO.getType(), output.getPhones().get(0).getType());
            assertEquals(phoneDTO.getUserId(), output.getPhones().get(0).getUser().getId());

            assertEquals(input.getPhones().size(), output.getPhones().size());
            assertEquals(roleDTO.getId(), output.getRoles().iterator().next().getId());
            assertEquals(roleDTO.getName(), output.getRoles().iterator().next().getName());
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
            UserDTO userDTO = UserMapper.entityToDto(userBO);

            assertEquals(userBO.getId(), userDTO.getId());
            assertEquals(userBO.getName(), userDTO.getName());
            assertEquals(userBO.getEmail(), userDTO.getEmail());
            assertEquals(userBO.getPassword(), userDTO.getPassword());
            assertEquals(userBO.getAccount().getId(), userDTO.getAccount().getId());
        }

        @Test
        void shouldCreateUserDTOWithRolesWhenUserBOAndRolesArePassed() {
            userBO.setRoles(Set.of(roleBO));

            UserDTO userDTO = UserMapper.entityToDto(userBO, userBO.getRoles());

            assertEquals(userBO.getId(), userDTO.getId());
            assertEquals(userBO.getName(), userDTO.getName());
            assertEquals(userBO.getEmail(), userDTO.getEmail());
            assertEquals(userBO.getPassword(), userDTO.getPassword());
            assertEquals(userBO.getAccount().getId(), userDTO.getAccount().getId());

            assertEquals(userBO.getRoles().size(), userDTO.getRoles().size());
            assertEquals(userBO.getRoles().iterator().next().getId(), userDTO.getRoles().iterator().next().getId());
            assertEquals(userBO.getRoles().iterator().next().getName(), userDTO.getRoles().iterator().next().getName());
        }

        @Test
        void shouldCreateUserDTOWithPhonesAndRolesWhenUserBOAndPhonesAndRolesArePassed() {
            userBO.setPhones(List.of(phoneBO));
            userBO.setRoles(Set.of(roleBO));

            UserDTO userDTO = UserMapper.entityToDto(userBO, userBO.getRoles(), userBO.getPhones());

            assertEquals(userBO.getId(), userDTO.getId());
            assertEquals(userBO.getName(), userDTO.getName());
            assertEquals(userBO.getEmail(), userDTO.getEmail());
            assertEquals(userBO.getPassword(), userDTO.getPassword());
            assertEquals(userBO.getAccount().getId(), userDTO.getAccount().getId());

            assertEquals(userBO.getPhones().size(), userDTO.getPhones().size());
            assertEquals(userBO.getPhones().get(0).getId(), userDTO.getPhones().get(0).getId());
            assertEquals(userBO.getPhones().get(0).getNumber(), userDTO.getPhones().get(0).getNumber());
            assertEquals(userBO.getPhones().get(0).getType(), userDTO.getPhones().get(0).getType());
            assertEquals(userBO.getPhones().get(0).getUser().getId(), userDTO.getPhones().get(0).getUserId());

            assertEquals(userBO.getRoles().size(), userDTO.getRoles().size());
            assertEquals(userBO.getRoles().iterator().next().getId(), userDTO.getRoles().iterator().next().getId());
            assertEquals(userBO.getRoles().iterator().next().getName(), userDTO.getRoles().iterator().next().getName());
        }

        @Test
        void shouldHandleEmptyPhonesAndRolesWhenUserBOIsPassed() {
            userBO.setPhones(Collections.emptyList());
            userBO.setRoles(Collections.emptySet());

            UserDTO userDTO = UserMapper.entityToDto(userBO, userBO.getRoles(), userBO.getPhones());

            assertEquals(0, userDTO.getPhones().size());
            assertEquals(0, userDTO.getRoles().size());
        }
    }

    @Nested
    class EntityToSummaryDto {

        @Test
        void shouldCreateUserSummaryDTOWhenUserBOIsPassed() {
            UserBO userBO = new UserBO();
            userBO.setId(ID);
            userBO.setName(NAME);
            userBO.setEmail(EMAIL);

            UserSummaryDTO userSummaryDTO = UserMapper.entityToSummaryDto(userBO);

            assertEquals(userBO.getId(), userSummaryDTO.getId());
            assertEquals(userBO.getName(), userSummaryDTO.getName());
            assertEquals(userBO.getEmail(), userSummaryDTO.getEmail());
        }
    }
}
