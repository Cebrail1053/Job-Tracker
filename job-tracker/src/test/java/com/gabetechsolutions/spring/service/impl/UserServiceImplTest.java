package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.builder.TestUserBuilder;
import com.gabetechsolutions.spring.common.UuidConverter;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.repository.UserRepository;
import com.gabetechsolutions.spring.service.TokenService;
import com.gabetechsolutions.spring.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private TokenService tokenService;

    @BeforeEach
    void init() {
        this.userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, tokenService);
    }

    @Test
    void testLoadUserByUsername_Success() {
        String email = "appuser09@email.com";
        User expectedUser = new TestUserBuilder()
              .withEmail(email)
              .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));
        UserDetails details = userService.loadUserByUsername(email);
        assertEquals(expectedUser.getEmail(), details.getUsername());
        assertEquals(expectedUser.getPassword(), details.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String email = "appuser09@email.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
    }

    //TODO: Update this test to use the new UserServiceImpl.signUpUser method
//    @Test
//    void testSignUpUser_Success() {
//        User expectedUser = new TestUserBuilder()
//              .withId(UuidConverter.uuidToBytes(UUID.randomUUID()))
//              .build();
//
//        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(Optional.empty());
//        when(bCryptPasswordEncoder.encode(expectedUser.getPassword())).thenReturn(expectedUser.getPassword());
//        when(userRepository.createUser(expectedUser)).thenReturn(expectedUser);
//
//        User createdUser = userService.signUpUser(expectedUser);
//
//        assertArrayEquals(expectedUser.getId(), createdUser.getId());
//        assertEquals(expectedUser.getFirstName(), createdUser.getFirstName());
//        assertEquals(expectedUser.getLastName(), createdUser.getLastName());
//        assertEquals(expectedUser.getEmail(), createdUser.getEmail());
//        assertEquals(expectedUser.getPassword(), createdUser.getPassword());
//    }

    @Test
    void testSignUpUser_UserAlreadyExists() {
        User expectedUser = new TestUserBuilder()
              .withId(UuidConverter.uuidToBytes(UUID.randomUUID()))
              .build();

        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(Optional.of(expectedUser));
        assertThrows(IllegalStateException.class, () -> userService.signUpUser(expectedUser));
    }

}