package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final static String USER_NOT_FOUND = "User with email %s not found";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String signUpUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
           throw new IllegalStateException("Email is already associated to an account");
           // TODO: Handle exception better
        }
        
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //TODO: Persist User to DB and Send confirmation token
        return "It Works";
    }
}
