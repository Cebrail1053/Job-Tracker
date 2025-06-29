package com.gabetechsolutions.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
              .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/", "/home")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
              .formLogin((form) -> form
                    .loginPage("/login")
                    .permitAll())
              .logout(LogoutConfigurer::permitAll);

        return http.build();
    }
}
