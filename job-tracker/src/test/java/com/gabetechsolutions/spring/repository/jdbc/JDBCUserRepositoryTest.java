package com.gabetechsolutions.spring.repository.jdbc;

import com.gabetechsolutions.spring.builder.TestUserBuilder;
import com.gabetechsolutions.spring.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JDBCUserRepositoryTest {

    private JDBCUserRepository userRepository;
    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        userRepository = new JDBCUserRepository(jdbcTemplate);
    }

    @Test
    void testFindByEmail_Success() {
        User expectedUser = new TestUserBuilder()
              .build();
        String email = expectedUser.getEmail();
        when(jdbcTemplate.query(anyString(), any(MapSqlParameterSource.class),
              any(JDBCUserRepository.UserRowMapper.class))).thenReturn(Collections.singletonList(expectedUser));

        Optional<User> actualUser = userRepository.findByEmail(email);

        assertTrue(actualUser.isPresent(), "User should be present");
        assertEquals(expectedUser, actualUser.get(), "Expected user should match the found user");
    }

    @Test
    void testFindByEmail_NotFound() {
        String email = "johndoe123@gmail.com";

        when(jdbcTemplate.query(anyString(), any(MapSqlParameterSource.class),
              any(JDBCUserRepository.UserRowMapper.class))).thenReturn(Collections.emptyList());

        Optional<User> actualUser = userRepository.findByEmail(email);

        assertFalse(actualUser.isPresent(), "User should not be present");
    }

    // TODO: Implement the createUser test cases
    @Test
    void testCreateUser_Success() {
        User userToCreate = new TestUserBuilder().build();
        when(jdbcTemplate.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(1);

        User createdUser = userRepository.createUser(userToCreate);
        assertEquals(userToCreate, createdUser, "Created user should match the input user");
    }

    @Test
    void testCreateUser_Failure() {
        User userToCreate = new TestUserBuilder().build();
        when(jdbcTemplate.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(0);

        assertThrows(DataAccessResourceFailureException.class, () -> userRepository.createUser(userToCreate),
              "Creating user should throw an exception when update fails");
    }
}