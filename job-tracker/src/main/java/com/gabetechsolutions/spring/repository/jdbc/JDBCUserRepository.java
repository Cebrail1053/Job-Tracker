package com.gabetechsolutions.spring.repository.jdbc;

import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.domain.enums.Role;
import com.gabetechsolutions.spring.repository.UserRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class JDBCUserRepository implements UserRepository {

    private static final String TABLE_NAME = "users";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = :email";
    private static final String CREATE_USER_SQL = "INSERT INTO " + TABLE_NAME + " (id, first_name, " +
          "last_name, email, password, role, locked, enabled) VALUES (:id, :firstName, :lastName, :email, " +
          ":password, :role, :locked, :enabled)";

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return null;
    }

    public class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) {
            try {
                User user = new User(
                      rs.getString("first_name"),
                      rs.getString("last_name"),
                      rs.getString("email"),
                      rs.getString("password"),
                      Role.valueOf(rs.getString("role"))
                );
                user.setId(rs.getBytes("id"));
                user.setLocked(rs.getBoolean("locked"));
                user.setEnabled(rs.getBoolean("enabled"));
                return user;
            } catch (Exception e) {
                throw new RuntimeException("Error mapping User from ResultSet", e);
            }
        }
    }

}
