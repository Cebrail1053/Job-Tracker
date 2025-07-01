package com.gabetechsolutions.spring.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class Application {

    private long jobId;
    private String jobTitle;
    private String company;
    private Optional<LocalDate> dateApplied;
    private Optional<String> location;
    private Optional<String> portalUrl;
    private Status status;
    private Optional<String> notes;
    private String userId;
}
