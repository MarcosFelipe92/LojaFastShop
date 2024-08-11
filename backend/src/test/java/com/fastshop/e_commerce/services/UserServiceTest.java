package com.fastshop.e_commerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.dtos.role.RoleDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.exceptions.service.InvalidEmailException;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;
import com.fastshop.e_commerce.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "example@example.com";
    private static final String NAME = "Test";
    private static final String PASSWORD = "123456";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccountBO mockAccountBO;

    @Mock
    private UserBO mockUserBO;

    @Mock
    private ShoppingCartBO mockShoppingCartBO;

    @Mock
    private RoleBO mockRoleBO;

    @Nested
    class ConfigurationTestsSetup {

        void setupBasicMocks() {
            when(roleService.findByName(any(String.class))).thenReturn(mockRoleBO);
            when(userRepository.save(any(UserBO.class))).thenReturn(mockUserBO);
            when(passwordEncoder.encode(any(String.class))).thenReturn(ENCODED_PASSWORD);

            when(mockUserBO.getId()).thenReturn(ID);
            when(mockUserBO.getName()).thenReturn(NAME);
            when(mockUserBO.getEmail()).thenReturn(EMAIL);
            when(mockUserBO.getPassword()).thenReturn(ENCODED_PASSWORD);
            when(mockUserBO.getAccount()).thenReturn(mockAccountBO);
            when(mockUserBO.getPhones()).thenReturn(new ArrayList<>());
            when(mockUserBO.getRoles()).thenReturn(new HashSet<>());

            when(mockAccountBO.getId()).thenReturn(ID);
            when(mockAccountBO.getShoppingCart()).thenReturn(mockShoppingCartBO);
            when(mockAccountBO.getUser()).thenReturn(mockUserBO);

            when(mockShoppingCartBO.getId()).thenReturn(ID);
            when(mockShoppingCartBO.getAccount()).thenReturn(mockAccountBO);
        }

        protected UserDTO createValidUserDTO() {
            List<PhoneDTO> phones = new ArrayList<>();
            Set<RoleDTO> roles = new HashSet<>();
            return new UserDTO(ID, NAME, EMAIL, PASSWORD, new AccountDTO(mockAccountBO),
                    phones, roles);
        }

        protected UserDTO createMinimalValidUserDTO() {
            List<PhoneDTO> phones = new ArrayList<>();
            Set<RoleDTO> roles = new HashSet<>();
            return new UserDTO(ID, NAME, EMAIL, PASSWORD, new AccountDTO(),
                    phones, roles);
        }
    }

    @Nested
    class CreateUser extends ConfigurationTestsSetup {

        @Test
        void shouldThrowExceptionWhenTryingRegisterUserWithEmailExisting() {
            when(userRepository.findByEmail(any(String.class))).thenReturn(new UserBO());

            UserDTO userDTO = createMinimalValidUserDTO();

            assertThrows(InvalidEmailException.class, () -> userService.create(userDTO));
        }

        @Test
        void shouldSaveAndReturnUserWhenRegisterUser() {
            setupBasicMocks();
            when(userRepository.findByEmail(any(String.class))).thenReturn(null);

            UserDTO userDTO = createValidUserDTO();

            UserDTO result = userService.create(userDTO);

            verify(userRepository).save(any(UserBO.class));
            assertNotNull(result);
            assertEquals(userDTO.getName(), result.getName());
            assertEquals(userDTO.getEmail(), result.getEmail());
            assertEquals(ENCODED_PASSWORD, result.getPassword());
        }
    }

}