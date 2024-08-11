package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.dtos.role.RoleDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.models.UserBO;

public class UserMapperTest {

    @Test
    void testDtoToEntity() {
        // Arrange
        PhoneDTO phoneDTO = new PhoneDTO(1L, "123456789", "MOBILE", 1L);
        Set<RoleDTO> rolesDTO = new HashSet<>();
        rolesDTO.add(new RoleDTO(1L, "USER"));

        UserDTO userDTO = new UserDTO(1L, "John Doe", "john.doe@example.com", "password", null, List.of(phoneDTO),
                rolesDTO);

        // Act
        UserBO userBO = UserMapper.dtoToEntity(userDTO);

        // Assert
        assertEquals(userDTO.getId(), userBO.getId());
        assertEquals(userDTO.getName(), userBO.getName());
        assertEquals(userDTO.getEmail(), userBO.getEmail());
        assertEquals(userDTO.getPassword(), userBO.getPassword());

        assertNotNull(userBO.getPhones());
        assertEquals(1, userBO.getPhones().size());
        assertEquals(phoneDTO.getNumber(), userBO.getPhones().get(0).getNumber());
        assertEquals(phoneDTO.getUserId(), userBO.getPhones().get(0).getUser().getId());

        assertNotNull(userBO.getRoles());
        assertEquals(1, userBO.getRoles().size());
        assertEquals(rolesDTO.iterator().next().getId(), userBO.getRoles().iterator().next().getId());
    }

}
