package com.fastshop.e_commerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.fastshop.e_commerce.auth.AuthService;
import com.fastshop.e_commerce.dtos.account.AccountDTO;
import com.fastshop.e_commerce.dtos.phone.PhoneDTO;
import com.fastshop.e_commerce.dtos.role.RoleDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.dtos.user.UserSummaryDTO;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.exceptions.service.InvalidEmailException;
import com.fastshop.e_commerce.mappers.UserMapper;
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
    private static final String ROLE_NAME = "BASIC";

    private static final String EXCEPTION_MESSAGE_USER_NOT_FOUND = "User not found.";
    private static final String EXCEPTION_MESSAGE_FIND_BY_ID = "You are not allowed to access to an user that does not you.";
    private static final String EXCEPTION_MESSAGE_CREATE = "Email Já existe.";
    private static final String EXCEPTION_MESSAGE_UPDATE = "You are not allowed to modify to an user that does not you.";
    private static final String EXCEPTION_MESSAGE_DELETE = "You are not allowed to delete to an user that does not you.";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthService authService;

    @Mock
    private AccountBO mockAccountBO;

    @Mock
    private UserBO mockUserBO;

    @Mock
    private ShoppingCartBO mockShoppingCartBO;

    @Mock
    private RoleBO mockRoleBO;

    @Mock
    private PhoneBO mockPhoneBO;

    @Mock
    private RoleDTO mockRoleDTO;

    @Mock
    private PhoneDTO mockPhoneDTO;

    @Mock
    private JwtAuthenticationToken mockToken;

    @Captor
    private ArgumentCaptor<UserBO> userArgumentCaptor;

    @Nested
    class FindAll {

        @Test
        void shouldReturnListOfUserWhenCalled() {
            // Arrange
            when(mockUserBO.getId()).thenReturn(ID);
            when(mockUserBO.getName()).thenReturn(NAME);
            when(mockUserBO.getEmail()).thenReturn(EMAIL);

            when(userRepository.findAll()).thenReturn(List.of(mockUserBO));

            // Act
            List<UserSummaryDTO> output = userService.findAll();

            // Assert
            assertEquals(ID, output.get(0).getId());
            assertEquals(NAME, output.get(0).getName());
            assertEquals(EMAIL, output.get(0).getEmail());
        }
    }

    @Nested
    class findById {

        @Test
        void shouldReturnUserWhenTheUserIsFoundAndUserHasPermission() {
            // Arrange
            setupMockUser();
            setupMockAccount();
            setupMockShoppingCart();
            when(mockPhoneBO.getId()).thenReturn(ID);
            when(mockPhoneBO.getUser()).thenReturn(mockUserBO);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserBO));
            when(authService.validateUserPermission(mockToken, ID)).thenReturn(true);

            // Act
            UserDTO output = userService.findById(ID, mockToken);

            // Assert
            assertEquals(ID, output.getId());

            // verificação do retorno das coleções associadas
            assertNotNull(output.getPhones());
            assertEquals(1, output.getPhones().size());
            assertNotNull(output.getRoles());
            assertEquals(1, output.getRoles().size());
        }

        @Test
        void shouldThrowExceptionWhenTheUserIsFoundButUserDoesNotHavePermission() {
            // Arrange
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserBO));
            when(authService.validateUserPermission(mockToken, ID)).thenReturn(false);

            // Act and Assert
            AccessDeniedException exception = assertThrows(AccessDeniedException.class,
                    () -> userService.findById(ID, mockToken));

            assertEquals(EXCEPTION_MESSAGE_FIND_BY_ID, exception.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenTheUserIsNotFound() {
            // Arrange
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

            // Act and Assert
            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> userService.findById(ID, mockToken));

            assertEquals(EXCEPTION_MESSAGE_USER_NOT_FOUND, exception.getMessage());
        }
    }

    @Nested
    class findByEmail {

        @Test
        void shouldReturnUserWhenTheUserIsFound() {
            // Arrange
            when(mockUserBO.getAccount()).thenReturn(mockAccountBO);
            when(mockUserBO.getEmail()).thenReturn(EMAIL);
            when(mockUserBO.getRoles()).thenReturn(Set.of(mockRoleBO));

            setupMockAccount();
            setupMockShoppingCart();

            when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(mockUserBO));

            // Act
            UserDTO output = userService.findByEmail(EMAIL);

            // Assert
            assertEquals(EMAIL, output.getEmail());

            // verificação do retorno das coleções associadas
            assertNotNull(output.getRoles());
            assertEquals(1, output.getRoles().size());
        }

        @Test
        void shouldThrowExceptionWhenTheUserIsNotFound() {
            // Arrange
            when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

            // Act and Assert
            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> userService.findByEmail(EMAIL));

            assertEquals(EXCEPTION_MESSAGE_USER_NOT_FOUND, exception.getMessage());
        }
    }

    @Nested
    class Create {

        @Test
        void shouldSaveAndReturnUserWhenRegisterUser() {
            setupMockUser();
            setupMockAccount();
            setupMockShoppingCart();
            when(mockPhoneBO.getId()).thenReturn(ID);
            when(mockPhoneBO.getUser()).thenReturn(mockUserBO);

            when(roleService.findByName(ROLE_NAME)).thenReturn(mockRoleBO);
            when(userRepository.save(userArgumentCaptor.capture())).thenReturn(mockUserBO);
            when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
            when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);

            UserDTO input = UserMapper.entityToDto(mockUserBO, mockUserBO.getRoles(), mockUserBO.getPhones());

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

            // Verificação dos phones
            assertNotNull(userCaptured.getPhones());
            assertEquals(1, userCaptured.getPhones().size());

            // Verificação das roles
            assertNotNull(userCaptured.getRoles());
            assertEquals(1, userCaptured.getRoles().size());
        }

        @Test
        void shouldThrowExceptionWhenTryingRegisterUserWithEmailExisting() {
            // Arrange
            when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(mockUserBO));

            UserDTO input = new UserDTO(ID, NAME, EMAIL, PASSWORD, new AccountDTO(),
                    Set.of(mockRoleDTO), List.of(mockPhoneDTO));

            // Act and Assert
            InvalidEmailException exception = assertThrows(InvalidEmailException.class,
                    () -> userService.create(input));

            assertEquals(EXCEPTION_MESSAGE_CREATE, exception.getMessage());
        }
    }

    @Nested
    class Update {

        @Test
        void shouldUpdateAndReturnUserWhenUserHasPermission() {
            ArrayList<PhoneBO> phones = new ArrayList<>();
            phones.add(mockPhoneBO);
            UserBO realUserBO = new UserBO(ID, NAME, EMAIL, PASSWORD, Set.of(mockRoleBO), mockAccountBO, phones);

            setupMockAccount();
            setupMockShoppingCart();
            when(mockPhoneBO.getUser()).thenReturn(realUserBO);

            when(userRepository.save(userArgumentCaptor.capture())).thenReturn(realUserBO);
            when(userRepository.findById(ID)).thenReturn(Optional.of(realUserBO));
            when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
            when(authService.validateUserPermission(mockToken, ID)).thenReturn(true);

            UserDTO input = UserMapper.entityToDto(realUserBO, realUserBO.getRoles(), realUserBO.getPhones());

            UserDTO output = userService.update(input, ID, mockToken);
            UserBO userCaptured = userArgumentCaptor.getValue();

            verify(userRepository, times(1)).save(userArgumentCaptor.capture());
            assertNotNull(output);
            assertEquals(ENCODED_PASSWORD, userCaptured.getPassword());

            // verificação das associações
            assertNotNull(userCaptured.getAccount());
            assertNotNull(userCaptured.getAccount().getUser());
            assertNotNull(userCaptured.getAccount().getShoppingCart());
            assertNotNull(userCaptured.getAccount().getShoppingCart().getAccount());
        }

        @Test
        void shouldThrowExceptionWhenTheUserIsNotFound() {
            // Arrange
            when(userRepository.findById(ID)).thenReturn(Optional.empty());

            UserDTO input = new UserDTO(ID, NAME, EMAIL, PASSWORD, new AccountDTO(),
                    Set.of(mockRoleDTO), List.of(mockPhoneDTO));

            // Act and Assert
            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> userService.update(input, ID, mockToken));

            assertEquals(EXCEPTION_MESSAGE_USER_NOT_FOUND, exception.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenTheUserIsFoundButUserDoesNotHavePermission() {
            // Arrange
            when(userRepository.findById(ID)).thenReturn(Optional.of(mockUserBO));
            when(authService.validateUserPermission(mockToken, ID)).thenReturn(false);

            UserDTO input = new UserDTO(ID, NAME, EMAIL, PASSWORD, new AccountDTO(),
                    Set.of(mockRoleDTO), List.of(mockPhoneDTO));

            // Act and Assert
            AccessDeniedException exception = assertThrows(AccessDeniedException.class,
                    () -> userService.update(input, ID, mockToken));

            assertEquals(EXCEPTION_MESSAGE_UPDATE, exception.getMessage());
        }
    }

    @Nested
    class Delete {

        @Test
        void shouldDeleteUserWhenTheUserIsFoundAndUserHasPermission() {
            // Arrange
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserBO));
            when(authService.validateUserPermission(mockToken, ID)).thenReturn(true);

            // Act
            userService.delete(ID, mockToken);

            // Assert
            verify(userRepository, times(1)).deleteById(ID);
        }

        @Test
        void shouldThrowExceptionWhenTheUserIsFoundButUserDoesNotHavePermission() {
            // Arrange
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserBO));
            when(authService.validateUserPermission(mockToken, ID)).thenReturn(false);

            // Act and Assert
            AccessDeniedException exception = assertThrows(AccessDeniedException.class,
                    () -> userService.delete(ID, mockToken));

            assertEquals(EXCEPTION_MESSAGE_DELETE, exception.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenTheUserIsNotFound() {
            // Arrange
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

            // Act and Assert
            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> userService.delete(ID, mockToken));

            assertEquals(EXCEPTION_MESSAGE_USER_NOT_FOUND, exception.getMessage());
        }
    }

    private void setupMockUser() {
        when(mockUserBO.getId()).thenReturn(ID);
        when(mockUserBO.getName()).thenReturn(NAME);
        when(mockUserBO.getEmail()).thenReturn(EMAIL);
        when(mockUserBO.getPassword()).thenReturn(PASSWORD);
        when(mockUserBO.getAccount()).thenReturn(mockAccountBO);

        when(mockUserBO.getPhones()).thenReturn(List.of(mockPhoneBO));

        when(mockUserBO.getRoles()).thenReturn(Set.of(mockRoleBO));
    }

    private void setupMockAccount() {
        when(mockAccountBO.getId()).thenReturn(ID);
        when(mockAccountBO.getShoppingCart()).thenReturn(mockShoppingCartBO);
        when(mockAccountBO.getUser()).thenReturn(mockUserBO);
    }

    private void setupMockShoppingCart() {
        when(mockShoppingCartBO.getId()).thenReturn(ID);
        when(mockShoppingCartBO.getAccount()).thenReturn(mockAccountBO);
    }

}