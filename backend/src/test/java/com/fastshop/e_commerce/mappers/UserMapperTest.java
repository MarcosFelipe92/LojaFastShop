package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.dtos.role.RoleDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.models.UserBO;

public class UserMapperTest {

    private static final Long ID = 1L;
    private static final String NAME = "John Doe";
    private static final String EMAIL = "johndoe@example.com";
    private static final String PASSWORD = "password123";
    private static final String TYPE_PHONE = "celular";
    private static final String NUMBER = "988888888";
    private static final String NAME_ROLE = "BASIC";

    @Test
    void shouldReturnInstanceUserBOWhenPassedUserDTO() {
        // Arrange
        PhoneDTO phoneDTO = new PhoneDTO(ID, NUMBER, TYPE_PHONE, ID);
        RoleDTO roleDTO = new RoleDTO(ID, NAME_ROLE);

        UserDTO input = new UserDTO(ID, NAME, EMAIL, PASSWORD, null, List.of(phoneDTO),
                Set.of(roleDTO));

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
