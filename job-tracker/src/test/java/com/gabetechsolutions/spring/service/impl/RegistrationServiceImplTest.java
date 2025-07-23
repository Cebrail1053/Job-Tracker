package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.common.validators.EmailValidator;
import com.gabetechsolutions.spring.service.RegistrationService;
import com.gabetechsolutions.spring.service.TokenService;
import com.gabetechsolutions.spring.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        String expectedToken = UUID.randomUUID().toString();

        when(emailValidator.test(request.email())).thenReturn(true);
        when(userService.signUpUser(any())).thenReturn(expectedToken);

        String actualResponse = registrationService.register(request);

        assertFalse(actualResponse.isEmpty());
        assertEquals(expectedToken, actualResponse);
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
        assertThrows(IllegalStateException.class, () -> registrationService.register(request));
    }

}