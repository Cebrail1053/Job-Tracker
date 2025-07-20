package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.builder.TestUserBuilder;
import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.common.UuidConverter;
import com.gabetechsolutions.spring.common.validators.EmailValidator;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.service.RegistrationService;
import com.gabetechsolutions.spring.service.TokenService;
import com.gabetechsolutions.spring.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    private RegistrationService registrationService;
    @Mock
    private EmailValidator emailValidator;
    @Mock
    private UserService userService;
    @Mock
    private TokenService tokenService;

    @BeforeEach
    void init() {
        this.registrationService = new RegistrationServiceImpl(emailValidator, userService, tokenService);
    }

    @Test
    void testRegister_Success() {
        RegistrationRequest request = new RegistrationRequest(
              "John",
              "Doe",
              "johndoe123@gmail.com",
              "password123"
        );
        byte[] uuidBytes = UuidConverter.uuidToBytes(UUID.randomUUID());
        User expectedUser = new TestUserBuilder()
              .withId(uuidBytes)
              .withFirstName(request.firstName())
              .withLastName(request.lastName())
              .withEmail(request.email())
              .withPassword(request.password())
              .build();

        when(emailValidator.test(request.email())).thenReturn(true);
        when(userService.signUpUser(any())).thenReturn(expectedUser);

        Optional<User> actualResponse = registrationService.register(request);

        assertTrue(actualResponse.isPresent());
        actualResponse.ifPresent(user -> {
            assertEquals(expectedUser.getFirstName(), user.getFirstName());
            assertEquals(expectedUser.getLastName(), user.getLastName());
            assertEquals(expectedUser.getEmail(), user.getEmail());
            assertEquals(expectedUser.getPassword(), user.getPassword());
            assertEquals(expectedUser.getRole(), user.getRole());
            assertArrayEquals(expectedUser.getId(), user.getId());
        });
    }

    @Test
    void testRegister_InvalidEmail() {
        RegistrationRequest request = new RegistrationRequest(
              "John",
              "Doe",
              "$johndo3.money@-gmail.com",
              "password123"
        );
        when(emailValidator.test(request.email())).thenReturn(false);
        assertThrows(IllegalStateException.class,() -> registrationService.register(request));
    }

}