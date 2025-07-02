package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.common.EmailValidator;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.domain.enums.Role;
import com.gabetechsolutions.spring.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    // TODO: Could this be encapsulated within the UserService?

    private final EmailValidator emailValidator;
    private final UserServiceImpl userService; // TODO: Should this be an interface reference

    @Override
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        // TODO: Better way of handling exception
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        //TODO: Potentially could use UUID for ID on SignUp
        return userService.signUpUser(
              new User(request.getFirstName(), request.getLastName(), request.getEmail(),
                    request.getPassword(), Role.USER)
        );
    }
}
