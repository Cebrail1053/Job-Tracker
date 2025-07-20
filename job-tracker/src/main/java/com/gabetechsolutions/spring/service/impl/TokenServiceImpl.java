package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.common.EmailSender;
import com.gabetechsolutions.spring.common.Path;
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
    private final EmailSender emailSender;

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
        String link = "http://localhost:8080" + Path.CONFIRMATION_URI + "confirm?token=" + token;
        emailSender.send(user.getEmail(), buildEmail(user.getFirstName(), link));
        return token;
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

    private String buildEmail(String name, String link) {
        return String.format("""
              <div style="font-family:Arial,sans-serif;font-size:16px;color:#333;">
                <h2 style="color:#1D70B8;">Confirm your email</h2>
                <p>Hi %s,</p>
                <p>Thank you for registering. Please click the link below to activate your account:</p>
                <p>
                  <a href="%s" style="background:#1D70B8;color:#fff;padding:10px 20px;text-decoration:none;border-radius:4px;">
                    Activate Now
                  </a>
                </p>
                <p style="font-size:14px;color:#888;">This link will expire in 15 minutes.</p>
                <p>See you soon!</p>
              </div>
              """, name, link);
    }
}
