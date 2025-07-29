package com.gabetechsolutions.spring.service;

import com.gabetechsolutions.spring.client.ApplicationRequest;
import com.gabetechsolutions.spring.domain.JobApplication;
import com.gabetechsolutions.spring.domain.User;

import java.util.List;

public interface JobApplicationService {

    List<JobApplication> getAllApplications(byte[] userId);

    JobApplication getApplicationById(long applicationId);

    JobApplication createApplication(ApplicationRequest request, User user);

    JobApplication updateApplication(JobApplication application);

    void deleteApplication(long applicationId);
}
