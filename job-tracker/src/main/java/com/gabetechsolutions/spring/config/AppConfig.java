package com.gabetechsolutions.spring.config;

import com.gabetechsolutions.spring.common.validators.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public EmailValidator emailValidator() {
        return new EmailValidator();
    }
}
