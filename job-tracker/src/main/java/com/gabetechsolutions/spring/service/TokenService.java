package com.gabetechsolutions.spring.service;

import com.gabetechsolutions.spring.domain.User;

import java.util.Optional;

public interface TokenService {

    String generateToken(User user);

    Optional<User> confirmToken(String token);
}
