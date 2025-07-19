package com.gabetechsolutions.spring.service;

import com.gabetechsolutions.spring.domain.User;

public interface TokenService {

    String generateToken(User user);

    boolean validateToken(String token);

    void confirmToken(String token);
}
