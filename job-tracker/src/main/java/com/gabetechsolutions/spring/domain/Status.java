package com.gabetechsolutions.spring.domain;

import java.util.Arrays;

public enum Status {
    ARCHIVED(0),
    IN_PROGRESS(1),
    INTERVIEWED(2);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Status fromCode(int code) {
        return Arrays.stream(Status.values())
              .filter(status -> status.getCode() == code)
              .findFirst()
              .orElseThrow(() -> new IllegalArgumentException("Unknown status code: " + code));
    }
}
