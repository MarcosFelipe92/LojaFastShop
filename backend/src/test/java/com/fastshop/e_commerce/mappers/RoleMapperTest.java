package com.fastshop.e_commerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.fastshop.e_commerce.dtos.role.RoleDTO;
import com.fastshop.e_commerce.models.RoleBO;

public class RoleMapperTest {

    private static final Long ID = 1L;
    private static final String NAME_ROLE = "BASIC";

    @Nested
    class DtoToEntity {

        @Test
        void shouldCreateRoleBOWhenRoleDTOIsPassed() {
            // Arrange
            RoleDTO input = new RoleDTO(ID, NAME_ROLE);

            // Act
            RoleBO output = RoleMapper.dtoToEntity(input);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME_ROLE, output.getName());
        }

    }

    @Nested
    class EntityToDto {

        @Test
        void shouldCreateRoleDTOWhenRoleBOIsPassed() {
            // Arrange
            RoleBO input = new RoleBO(ID, NAME_ROLE);

            // Act
            RoleDTO output = RoleMapper.entityToDto(input);

            // Assert
            assertEquals(ID, output.getId());
            assertEquals(NAME_ROLE, output.getName());
        }

    }
}
