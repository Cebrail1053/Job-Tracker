package com.gabetechsolutions.spring.repository;

import com.gabetechsolutions.spring.domain.JobApplication;

import java.util.List;

public interface JobApplicationRepository {

    List<JobApplication> findAllByUserId(byte[] userId);

    JobApplication findByApplicationId(long applicationId);

    JobApplication createApplication(JobApplication jobApplication);

    JobApplication updateApplication(JobApplication jobApplication);

    void deleteApplication(long applicationId);
}
