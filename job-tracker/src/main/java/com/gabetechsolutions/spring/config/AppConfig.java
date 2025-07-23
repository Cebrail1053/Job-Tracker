package com.gabetechsolutions.spring.config;

import com.gabetechsolutions.spring.common.EmailSender;
import com.gabetechsolutions.spring.common.validators.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public EmailValidator emailValidator() {
        return new EmailValidator();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public EmailSender emailSender(JavaMailSender mailSender) {
        return new EmailSender(mailSender);
    }
}
