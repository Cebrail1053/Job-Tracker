package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.common.UuidConverter;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.repository.UserRepository;
import com.gabetechsolutions.spring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final static String USER_NOT_FOUND = "User with email:%s not found";
    private final static String USER_ASSOCIATED_TO_EMAIL = "%s is already associated to an account";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    @Override
    public User signUpUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException(String.format(USER_ASSOCIATED_TO_EMAIL, user.getEmail()));
        }
        // Encode the password before saving the user
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // Generate a new UUID for the user
        user.setId(UuidConverter.uuidToBytes(UUID.randomUUID()));

        User createdUser = userRepository.createUser(user);
        //TODO: Send confirmation token
        return createdUser != null ? createdUser : null;
    }
}
