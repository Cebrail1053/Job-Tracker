package com.gabetechsolutions.spring.common.validators;

import java.util.function.Predicate;

public class EmailValidator implements Predicate<String> {

    private final String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9_-]+(\\" +
          ".[A-Za-z0-9_-]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean test(String s) {
        return s.matches(regex);
    }
}
