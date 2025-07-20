package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.domain.token.ConfirmationToken;
import com.gabetechsolutions.spring.repository.ConfirmationTokenRepository;
import com.gabetechsolutions.spring.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
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
    public Optional<User> confirmToken(String token) {
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Token already confirmed");
        }

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        return tokenRepository.updateConfirmationToken(null, confirmationToken) ?
                Optional.of(confirmationToken.getUser()) :
                Optional.empty();
    }
}
