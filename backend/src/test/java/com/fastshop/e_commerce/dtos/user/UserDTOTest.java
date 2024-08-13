package com.fastshop.e_commerce.dtos.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.PhoneBO;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;

@ExtendWith(MockitoExtension.class)
public class UserDTOTest {

    private static final Long ID = 1L;
    private static final String NAME = "John Doe";
    private static final String EMAIL = "johndoe@example.com";
    private static final String PASSWORD = "password123";
    private static final String TYPE_PHONE = "celular";
    private static final String NUMBER = "988888888";
    private static final String NAME_ROLE = "BASIC";

    @Mock
    private UserBO mockUserBO;

    @Mock
    private AccountBO mockAccountBO;

    @Mock
    private ShoppingCartBO mockShoppingCartBO;

    @Mock
    private PhoneBO mockPhoneBO;

    @Mock
    private RoleBO mockRoleBO;

    @BeforeEach
    void setup() {
        when(mockShoppingCartBO.getId()).thenReturn(ID);
        when(mockShoppingCartBO.getAccount()).thenReturn(mockAccountBO);

        when(mockAccountBO.getId()).thenReturn(ID);
        when(mockAccountBO.getUser()).thenReturn(mockUserBO);
        when(mockAccountBO.getShoppingCart()).thenReturn(mockShoppingCartBO);

        when(mockUserBO.getId()).thenReturn(ID);
        when(mockUserBO.getName()).thenReturn(NAME);
        when(mockUserBO.getEmail()).thenReturn(EMAIL);
        when(mockUserBO.getPassword()).thenReturn(PASSWORD);
    }

    @Test
    void shouldReturnSimpleInstanceUserDTOWhenPassedAnUserBO() {
        when(mockUserBO.getAccount()).thenReturn(mockAccountBO);

        UserDTO userDTO = new UserDTO(mockUserBO);

        assertEquals(mockUserBO.getId(), userDTO.getId());
        assertEquals(mockUserBO.getName(), userDTO.getName());
        assertEquals(mockUserBO.getEmail(), userDTO.getEmail());
        assertEquals(mockUserBO.getPassword(), userDTO.getPassword());
        assertEquals(mockUserBO.getAccount().getId(), userDTO.getAccount().getId());
    }

    @Test
    void shouldReturnInstanceUserDTOWithRolesWhenPassedAnUserBOAndRoles() {
        when(mockRoleBO.getId()).thenReturn(ID);
        when(mockRoleBO.getName()).thenReturn(NAME_ROLE);

        when(mockUserBO.getAccount()).thenReturn(mockAccountBO);
        when(mockUserBO.getRoles()).thenReturn(Set.of(mockRoleBO));

        UserDTO userDTO = new UserDTO(mockUserBO, mockUserBO.getRoles());

        assertEquals(mockUserBO.getId(), userDTO.getId());
        assertEquals(mockUserBO.getName(), userDTO.getName());
        assertEquals(mockUserBO.getEmail(), userDTO.getEmail());
        assertEquals(mockUserBO.getPassword(), userDTO.getPassword());
        assertEquals(mockUserBO.getAccount().getId(), userDTO.getAccount().getId());

        assertEquals(mockUserBO.getRoles().size(), userDTO.getRoles().size());
        assertEquals(mockUserBO.getRoles().iterator().next().getId(), userDTO.getRoles().iterator().next().getId());
        assertEquals(mockUserBO.getRoles().iterator().next().getName(), userDTO.getRoles().iterator().next().getName());
    }

    @Test
    void shouldHandleEmptyPhonesAndRoles() {
        when(mockUserBO.getAccount()).thenReturn(mockAccountBO);
        when(mockUserBO.getPhones()).thenReturn(Collections.emptyList());
        when(mockUserBO.getRoles()).thenReturn(Collections.emptySet());

        UserDTO userDTO = new UserDTO(mockUserBO, mockUserBO.getPhones(), mockUserBO.getRoles());

        assertEquals(0, userDTO.getPhones().size());
        assertEquals(0, userDTO.getRoles().size());
    }

    @Test
    void shouldReturnInstanceUserDTOWithPhonesAndRolesWhenPassedAnUserBOAndPhonesAndRoles() {
        when(mockPhoneBO.getId()).thenReturn(ID);
        when(mockPhoneBO.getType()).thenReturn(TYPE_PHONE);
        when(mockPhoneBO.getNumber()).thenReturn(NUMBER);
        when(mockPhoneBO.getUser()).thenReturn(mockUserBO);

        when(mockRoleBO.getId()).thenReturn(ID);
        when(mockRoleBO.getName()).thenReturn(NAME_ROLE);

        when(mockUserBO.getAccount()).thenReturn(mockAccountBO);
        when(mockUserBO.getPhones()).thenReturn(List.of(mockPhoneBO));
        when(mockUserBO.getRoles()).thenReturn(Set.of(mockRoleBO));

        UserDTO userDTO = new UserDTO(mockUserBO, mockUserBO.getPhones(), mockUserBO.getRoles());

        assertEquals(mockUserBO.getId(), userDTO.getId());
        assertEquals(mockUserBO.getName(), userDTO.getName());
        assertEquals(mockUserBO.getEmail(), userDTO.getEmail());
        assertEquals(mockUserBO.getPassword(), userDTO.getPassword());
        assertEquals(mockUserBO.getAccount().getId(), userDTO.getAccount().getId());

        assertEquals(mockUserBO.getPhones().size(), userDTO.getPhones().size());
        assertEquals(mockUserBO.getPhones().get(0).getId(), userDTO.getPhones().get(0).getId());
        assertEquals(mockUserBO.getPhones().get(0).getNumber(), userDTO.getPhones().get(0).getNumber());
        assertEquals(mockUserBO.getPhones().get(0).getType(), userDTO.getPhones().get(0).getType());
        assertEquals(mockUserBO.getPhones().get(0).getUser().getId(), userDTO.getPhones().get(0).getUserId());

        assertEquals(mockUserBO.getRoles().size(), userDTO.getRoles().size());
        assertEquals(mockUserBO.getRoles().iterator().next().getId(), userDTO.getRoles().iterator().next().getId());
        assertEquals(mockUserBO.getRoles().iterator().next().getName(), userDTO.getRoles().iterator().next().getName());
    }

}
