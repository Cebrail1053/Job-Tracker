package com.gabetechsolutions.spring.service;

import com.gabetechsolutions.spring.client.RegistrationRequest;
import org.springframework.stereotype.Service;

public interface RegistrationService {

    String register(RegistrationRequest request);
}
