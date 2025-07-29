package com.gabetechsolutions.spring.domain;

import com.gabetechsolutions.spring.domain.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class JobApplication {
    private long id;
    private String jobTitle;
    private String company;
    private LocalDate dateApplied;
    private String location;
    private String portalUrl;
    private ApplicationStatus status;
    private String notes;
    private byte[] userId;
}
