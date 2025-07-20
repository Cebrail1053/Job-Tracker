package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.common.validators.EmailValidator;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.domain.enums.Role;
import com.gabetechsolutions.spring.domain.token.ConfirmationToken;
import com.gabetechsolutions.spring.service.RegistrationService;
import com.gabetechsolutions.spring.service.TokenService;
import com.gabetechsolutions.spring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final EmailValidator emailValidator;
    private final UserService userService;
    private final TokenService tokenService;

    private static final String EMAIL_NOT_VALID = "%s is not a valid email address";

    @Override
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.email());

        if (!isValidEmail) {
            throw new IllegalStateException(String.format(EMAIL_NOT_VALID, request.email()));
        }

        return userService.signUpUser(
              new User(request.firstName(), request.lastName(), request.email(),
                    request.password(), Role.USER)
        );
    }

    @Override
    public void confirmToken(String token) {
        tokenService.confirmToken(token).ifPresent(userService::enableUser);
    }
}
