package com.gabetechsolutions.spring.repository.jdbc;

import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.domain.enums.Role;
import com.gabetechsolutions.spring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JDBCUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String TABLE_NAME = "user";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = :email";
    private static final String CREATE_USER_SQL = "INSERT INTO " + TABLE_NAME + " (id, first_name, " +
          "last_name, email, password, role, locked, enabled) VALUES (:id, :firstName, :lastName, :email, " +
          ":password, :role, :locked, :enabled)";
    private static final String ENABLE_USER_SQL = "UPDATE " + TABLE_NAME + " SET enabled = true WHERE id = " +
          ":id";

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
              .addValue("email", email);
        try {
            return jdbcTemplate.query(FIND_BY_EMAIL_SQL, parameters, new UserRowMapper()).stream().findFirst();
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error finding user by email: " + email, e);
        }
    }

    @Override
    @Transactional
    public User createUser(User user) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
              .addValue("id", user.getId())
              .addValue("firstName", user.getFirstName())
              .addValue("lastName", user.getLastName())
              .addValue("email", user.getEmail())
              .addValue("password", user.getPassword())
              .addValue("role", user.getRole().name())
              .addValue("locked", user.isLocked())
              .addValue("enabled", user.isEnabled());
        try {
            int rowsAffected = jdbcTemplate.update(CREATE_USER_SQL, parameters);
            if (rowsAffected == 0) {
                throw new DataAccessResourceFailureException("Failed to create user: " + user.getEmail());
            }
            return user;
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error creating user: " + user.getEmail(), e);
        }
    }

    @Override
    @Transactional
    public void enableUser(User user) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
              .addValue("id", user.getId());
        try {
            int rowsAffected = jdbcTemplate.update(ENABLE_USER_SQL, parameters);
            if (rowsAffected == 0) {
                throw new DataAccessResourceFailureException("Failed to enable user: " + user.getEmail());
            }
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error enabling user: " + user.getEmail(), e);
        }
    }

    public static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
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
            } catch (SQLException e) {
                throw new SQLException("Error mapping User from ResultSet", e);
            }
        }
    }
}
