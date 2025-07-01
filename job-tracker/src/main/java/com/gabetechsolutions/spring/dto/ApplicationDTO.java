package com.gabetechsolutions.spring.dto;

import java.time.LocalDate;

public record ApplicationDTO(long jobId, String jobTitle, String company, LocalDate dateApplied,
                             String location, String portalUrl, StatusDTO status, String notes) {}
