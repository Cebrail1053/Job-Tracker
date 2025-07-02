package com.gabetechsolutions.spring.repository;

import com.gabetechsolutions.spring.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    User createUser(User user);
}
