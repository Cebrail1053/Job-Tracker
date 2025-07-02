package com.gabetechsolutions.spring.service;

import com.gabetechsolutions.spring.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String signUpUser(User user);
}
