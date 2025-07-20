package com.gabetechsolutions.spring.service;

import com.gabetechsolutions.spring.client.RegistrationRequest;
import com.gabetechsolutions.spring.domain.User;

import java.util.Optional;

public interface RegistrationService {

    String register(RegistrationRequest request);

    void confirmToken(String token);
}
