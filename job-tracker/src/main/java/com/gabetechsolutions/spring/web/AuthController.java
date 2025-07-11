package com.gabetechsolutions.spring.web;

import com.gabetechsolutions.spring.client.LoginRequest;
import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.client.AuthResponse;
import com.gabetechsolutions.spring.common.Path;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private AuthenticationManager authenticationManager;

    @PostMapping(Path.LOGIN_URI)
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication =
              authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new AuthResponse("User authenticated successfully",
              loginRequest.email()));
    }

    @PostMapping(Path.SIGNUP_URI)
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegistrationRequest request) {
        Optional<User> user = registrationService.register(request);
        AuthResponse response = user.map(value ->
                    new AuthResponse("User registered successfully", value.getEmail()))
              .orElseGet(() ->
                    new AuthResponse("User registration failed", request.email()));
        return ResponseEntity.ok(response);
    }
}
