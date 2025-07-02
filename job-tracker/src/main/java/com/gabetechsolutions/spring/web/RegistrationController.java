package com.gabetechsolutions.spring.web;

import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
// TODO: Should this include "/api/v1"? Maybe signup/login should be root
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}
