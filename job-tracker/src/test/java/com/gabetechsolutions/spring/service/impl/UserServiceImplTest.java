package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.builder.TestUserBuilder;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.repository.UserRepository;
import com.gabetechsolutions.spring.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void init() {
        this.userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
    }

    @Test
    void testLoadUserByUsername_Success() {
        String email = "appuser09@email.com";
        User expectedUser = new TestUserBuilder()
              .withEmail(email)
              .build();
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));
        UserDetails details = userService.loadUserByUsername(email);
        assertEquals(expectedUser.getEmail(), details.getUsername());
        assertEquals(expectedUser.getPassword(), details.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String email = "appuser09@email.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
    }

    @Test
    void testSignUpUser_Success() {
        
    }

    @Test
    void testSignUpUser_UserAlreadyExists() {

    }

}