package com.gabetechsolutions.spring.config;

import com.gabetechsolutions.spring.common.Path;
import com.gabetechsolutions.spring.service.UserService;
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
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class SecurityConfig {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TODO: enable csrf, temporarily disabled during development
        http
              .csrf(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests((requests) -> requests
                    .requestMatchers(Path.BASE_URI,
                          Path.SIGNUP_URI
                    )
                    .permitAll()
                    .anyRequest()
                    .authenticated())
              .formLogin((form) -> form
                    .loginPage(Path.LOGIN_URI)
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
