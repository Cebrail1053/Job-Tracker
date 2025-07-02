package com.gabetechsolutions.spring.config;

import com.gabetechsolutions.spring.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TODO: enable csrf, temporarily disabled during development
        http
              .csrf(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/",
                          "/api/v1/registration"
                    )
                    .permitAll()
                    .anyRequest()
                    .authenticated())
              .formLogin((form) -> form
                    .loginPage("/login")
                    .permitAll())
              .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }

}
