package com.gabetechsolutions.spring.repository;

import com.gabetechsolutions.spring.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository {

    Optional<User> findByEmail(String email);
}
