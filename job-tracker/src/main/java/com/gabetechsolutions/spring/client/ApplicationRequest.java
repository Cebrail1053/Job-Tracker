package com.gabetechsolutions.spring.client;

import java.time.LocalDate;

public record ApplicationRequest(String jobTitle, String company, LocalDate dateApplied, String location,
                                 String portalUrl, String notes) {
}
