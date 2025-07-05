package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.common.validators.EmailValidator;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.domain.enums.Role;
import com.gabetechsolutions.spring.service.RegistrationService;
import com.gabetechsolutions.spring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final EmailValidator emailValidator;
    private final UserService userService;

    private static final String EMAIL_NOT_VALID = "%s is not a valid email address";

    @Override
    public Optional<User> register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.email());

        if (!isValidEmail) {
            throw new IllegalStateException(String.format(EMAIL_NOT_VALID, request.email()));
        }

        User userCreated = userService.signUpUser(
              new User(request.firstName(), request.lastName(), request.email(),
                    request.password(), Role.USER)
        );
        return Optional.of(userCreated);
    }
}
