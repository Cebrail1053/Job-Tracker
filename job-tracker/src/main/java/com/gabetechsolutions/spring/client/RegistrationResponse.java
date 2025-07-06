package com.gabetechsolutions.spring.client;

import lombok.Data;

import java.util.Objects;

@Data
public class RegistrationResponse {
    private String message;
    private String email;
}
