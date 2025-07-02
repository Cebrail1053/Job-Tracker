package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.service.RegistrationService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public String register(RegistrationRequest request) {
        return "It Works";
    }
}
