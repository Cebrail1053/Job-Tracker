package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.domain.token.ConfirmationToken;
import com.gabetechsolutions.spring.repository.ConfirmationTokenRepository;
import com.gabetechsolutions.spring.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final ConfirmationTokenRepository tokenRepository;

    @Override
    public String generateToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
              token,
              LocalDateTime.now(),
              LocalDateTime.now().plusMinutes(15),
              user
        );
        tokenRepository.save(confirmationToken);
        // TODO: Send Email with the token to the user
        return "";
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }

    @Override
    public void confirmToken(String token) {

    }
}
