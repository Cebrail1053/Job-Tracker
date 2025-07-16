package com.gabetechsolutions.spring.web;

import com.gabetechsolutions.spring.client.AuthResponse;
import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.common.Path;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthController {

    private RegistrationService registrationService;

    @PostMapping(Path.SIGNUP_URI)
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegistrationRequest request) {
        Optional<User> user = registrationService.register(request);
        AuthResponse response = user.map(value ->
                    new AuthResponse("User registered successfully", value.getEmail()))
              .orElseGet(() ->
                    new AuthResponse("User registration failed", request.email()));
        return ResponseEntity.ok(response);
    }

    // TODO: Move this to a separate controller
    @GetMapping(Path.LOGIN_URI)
    public String loginPage() {
        return "login";
    }
}
