package com.gabetechsolutions.spring.service;

import com.gabetechsolutions.spring.domain.JobApplication;

import java.util.List;

public interface JobApplicationService {

    List<JobApplication> getAllApplications(byte[] userId);

    JobApplication getApplicationById(long applicationId);

    JobApplication createApplication(JobApplication application);

    JobApplication updateApplication(JobApplication application);

    void deleteApplication(long applicationId);
}
