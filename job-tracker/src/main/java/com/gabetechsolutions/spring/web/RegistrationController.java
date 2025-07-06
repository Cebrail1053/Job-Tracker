package com.gabetechsolutions.spring.web;

import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.client.RegistrationResponse;
import com.gabetechsolutions.spring.common.Path;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping(Path.SIGNUP_URI)
    public RegistrationResponse register(@RequestBody RegistrationRequest request) {
        Optional<User> user = registrationService.register(request);
        RegistrationResponse response = new RegistrationResponse();
        if (user.isPresent()) {
            response.setMessage("User registered successfully");
            response.setEmail(user.get().getEmail());
        } else {
            response.setMessage("User registration failed");
            response.setEmail(request.email());
        }
        return response;
    }
}
