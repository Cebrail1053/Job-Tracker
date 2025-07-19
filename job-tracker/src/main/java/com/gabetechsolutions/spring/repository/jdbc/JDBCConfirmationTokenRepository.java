package com.gabetechsolutions.spring.repository.jdbc;

import com.gabetechsolutions.spring.domain.token.ConfirmationToken;
import com.gabetechsolutions.spring.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
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
public class JDBCConfirmationTokenRepository implements ConfirmationTokenRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String TABLE_NAME = "confirmation_token";
    private static final String USER_TABLE_NAME = "user";
    private static final String FIND_BY_TOKEN_SQL =
          "SELECT T.*, U.* FROM " + TABLE_NAME + " T JOIN " + USER_TABLE_NAME + " U ON T.user_id = U.id " +
                "WHERE token = :token";
    private static final String SAVE_TOKEN_SQL = "INSERT INTO " + TABLE_NAME + " (token, created_at, " +
          "expires_at, confirmed_at, user_id) " +
          "VALUES (:token, :createdAt, :expiresAt, :confirmedAt, :userId)";

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
              .addValue("token", token);
        try {
            return jdbcTemplate.query(FIND_BY_TOKEN_SQL, parameters, new ConfirmationTokenRowMapper())
                  .stream().findFirst();
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error finding confirmation token: " + token, e);
        }
    }

    @Override
    @Transactional
    public void save(ConfirmationToken confirmationToken) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
              .addValue("token", confirmationToken.getToken())
              .addValue("createdAt", confirmationToken.getCreatedAt())
              .addValue("expiresAt", confirmationToken.getExpiresAt())
              .addValue("confirmedAt", confirmationToken.getConfirmedAt())
              .addValue("userId", confirmationToken.getUser().getId());
        try {
            int rowsAffected = jdbcTemplate.update(SAVE_TOKEN_SQL, parameters);
            if (rowsAffected == 0) {
                throw new DataAccessResourceFailureException("Failed to save confirmation token: " +
                      confirmationToken.getToken());
            }
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error saving confirmation token: " + confirmationToken.getToken(), e);
        }
    }

    public static class ConfirmationTokenRowMapper implements RowMapper<ConfirmationToken> {

        private static final JDBCUserRepository.UserRowMapper USER_ROW_MAPPER =
              new JDBCUserRepository.UserRowMapper();

        @Override
        public ConfirmationToken mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConfirmationToken token = new ConfirmationToken();
            token.setId(rs.getLong("token_id"));
            token.setToken(rs.getString("token"));
            token.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            token.setExpiresAt(rs.getTimestamp("expires_at").toLocalDateTime());
            token.setConfirmedAt(rs.getTimestamp("confirmed_at") != null ?
                  rs.getTimestamp("confirmed_at").toLocalDateTime() : null);
            token.setUser(USER_ROW_MAPPER.mapRow(rs, rowNum));
            return token;
        }
    }
}
