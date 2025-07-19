package com.gabetechsolutions.spring.repository;

import com.gabetechsolutions.spring.domain.token.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenRepository {

    Optional<ConfirmationToken> findByToken(String token);

    void save(ConfirmationToken confirmationToken);
}
