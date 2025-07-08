package com.gabetechsolutions.spring.builder;

import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.domain.enums.Role;

public class TestUserBuilder {
    private byte[] id = new byte[16]; // Default to a 16-byte array for UUID
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role = Role.USER;
    private boolean locked = false;
    private boolean enabled = false;

    public TestUserBuilder() {
        this.firstName = "John";
        this.lastName = "Doe";
        this.email = "johndoe123@gmail.com";
        this.password = "password123";
    }

    public TestUserBuilder withId(byte[] id) {
        this.id = id;
        return this;
    }

    public TestUserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public TestUserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public TestUserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public TestUserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public TestUserBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    public TestUserBuilder withLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    public TestUserBuilder withEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public User build() {
        return new User(id, firstName, lastName, email, password, role, locked, enabled);
    }
}
