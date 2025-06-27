package com.gabetechsolutions.spring.entity;

import java.time.LocalDate;

public record ApplicationDTO(int jobId, String jobTitle, String company, LocalDate dateApplied,
                             String location, String portalUrl, StatusDTO status, String notes,
                             String userId) {}
