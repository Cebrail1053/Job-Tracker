package com.gabetechsolutions.spring.client;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest { //TODO: Should this be a record?

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
