package com.fastshop.e_commerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.dtos.role.RoleDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.exceptions.service.InvalidEmailException;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.PhoneBO;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;
import com.fastshop.e_commerce.models.UserBO;
import com.fastshop.e_commerce.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
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

    @Mock
    private JwtAuthenticationToken mockToken;

    @Captor
    private ArgumentCaptor<UserBO> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> idArgumentCaptor;

    @Nested
    class ConfigurationTestsSetup {
        void setupMockUser() {
            when(mockUserBO.getId()).thenReturn(ID);
            when(mockUserBO.getName()).thenReturn(NAME);
            when(mockUserBO.getEmail()).thenReturn(EMAIL);
            when(mockUserBO.getPassword()).thenReturn(PASSWORD);
            when(mockUserBO.getAccount()).thenReturn(mockAccountBO);

            List<PhoneBO> phones = new ArrayList<>();
            phones.add(new PhoneBO(1L, "123456789", "celular", mockUserBO));
            when(mockUserBO.getPhones()).thenReturn(phones);

            Set<RoleBO> roles = new HashSet<>();
            roles.add(mockRoleBO);
            when(mockUserBO.getRoles()).thenReturn(roles);
        }

        void setupMockAccount() {
            when(mockAccountBO.getId()).thenReturn(ID);
            when(mockAccountBO.getShoppingCart()).thenReturn(mockShoppingCartBO);
            when(mockAccountBO.getUser()).thenReturn(mockUserBO);
        }

        void setupMockShoppingCart() {
            when(mockShoppingCartBO.getId()).thenReturn(ID);
            when(mockShoppingCartBO.getAccount()).thenReturn(mockAccountBO);
        }
    }

    @Nested
    class CreateUser extends ConfigurationTestsSetup {

        @Test
        void shouldThrowExceptionWhenTryingRegisterUserWithEmailExisting() {
            when(userRepository.findByEmail(any(String.class))).thenReturn(new UserBO());

            List<PhoneDTO> phones = new ArrayList<>();
            Set<RoleDTO> roles = new HashSet<>();
            UserDTO input = new UserDTO(ID, NAME, EMAIL, PASSWORD, new AccountDTO(),
                    phones, roles);

            assertThrows(InvalidEmailException.class, () -> userService.create(input));
        }

        @Test
        void shouldSaveAndReturnUserWhenRegisterUser() {
            setupMockUser();
            setupMockAccount();
            setupMockShoppingCart();
            when(roleService.findByName(any(String.class))).thenReturn(mockRoleBO);
            when(userRepository.save(userArgumentCaptor.capture())).thenReturn(mockUserBO);
            when(passwordEncoder.encode(any(String.class))).thenReturn(ENCODED_PASSWORD);
            when(userRepository.findByEmail(any(String.class))).thenReturn(null);

            UserDTO input = new UserDTO(mockUserBO);

            UserDTO output = userService.create(input);
            UserBO userCaptured = userArgumentCaptor.getValue();

            verify(userRepository, times(1)).save(userArgumentCaptor.capture());
            assertNotNull(output);
            assertEquals(ENCODED_PASSWORD, userCaptured.getPassword());

            // verificação das associações
            assertNotNull(userCaptured.getAccount());
            assertNotNull(userCaptured.getAccount().getUser());
            assertNotNull(userCaptured.getAccount().getShoppingCart());
            assertNotNull(userCaptured.getAccount().getShoppingCart().getAccount());

            // Verificação das roles
            assertNotNull(userCaptured.getRoles());
            assertEquals(1, userCaptured.getRoles().size());
            assertEquals(mockRoleBO, userCaptured.getRoles().iterator().next());
        }
    }

    @Nested
    class findById extends ConfigurationTestsSetup {

        @Test
        void shouldReturnUserWhenValidIdPassed() {
            setupMockUser();
            setupMockAccount();
            setupMockShoppingCart();
            when(userRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(mockUserBO));
            when(mockToken.getName()).thenReturn(String.valueOf(ID));

            UserDTO output = userService.findById(ID, mockToken);

            verify(userRepository, times(3)).findById(idArgumentCaptor.capture());
            assertNotNull(output);
            assertEquals(ID, output.getId());
            assertEquals(ID, idArgumentCaptor.getValue());

            // verificação do retorno das coleções associadas
            assertNotNull(output.getPhones());
            assertEquals(1, output.getPhones().size());
            assertNotNull(output.getRoles());
            assertEquals(1, output.getRoles().size());
        }
    }

}