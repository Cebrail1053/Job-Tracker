package com.gabetechsolutions.spring.web;

import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.client.RegistrationResponse;
import com.gabetechsolutions.spring.common.Path;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(Path.SIGNUP_URI)
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody RegistrationRequest request) {
        Optional<User> user = registrationService.register(request);
        RegistrationResponse response = user.map(value ->
                    new RegistrationResponse("User registered successfully", value.getEmail()))
              .orElseGet(() ->
                    new RegistrationResponse("User registration failed", request.email()));
        return ResponseEntity.ok(response);
    }

    // TODO: Endpoint is not being reached, check security configuration
    @GetMapping(Path.CONFIRMATION_URI)
    public ResponseEntity<String> confirmUser(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
        return ResponseEntity.ok("User confirmed successfully");
    }

}
