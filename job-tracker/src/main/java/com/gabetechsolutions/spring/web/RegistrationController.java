package com.gabetechsolutions.spring.web;

import com.gabetechsolutions.spring.client.RegistrationRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    public String register(@RequestBody RegistrationRequest request) {
        return "works";
    }
}
