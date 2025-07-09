package com.gabetechsolutions.spring.client;

public record RegistrationRequest(String firstName, String lastName, String email,
                                  String password) {
}
