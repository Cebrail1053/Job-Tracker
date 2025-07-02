package com.gabetechsolutions.spring.repository.jdbc;

import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true) // TODO: Research if this is needed
public class JDBCUserRepository implements UserRepository {

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

}
