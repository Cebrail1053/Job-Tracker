package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.client.ApplicationRequest;
import com.gabetechsolutions.spring.domain.JobApplication;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.domain.enums.ApplicationStatus;
import com.gabetechsolutions.spring.repository.JobApplicationRepository;
import com.gabetechsolutions.spring.service.JobApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobApplicaitonServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    @Override
    public List<JobApplication> getAllApplications(byte[] userId) {
        if (userId == null || userId.length == 0) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        return jobApplicationRepository.findAllByUserId(userId);
    }

    @Override
    public JobApplication getApplicationById(long applicationId) {
        if (applicationId <= 0) {
            throw new IllegalArgumentException("Application ID must be greater than zero");
        }

        return jobApplicationRepository.findByApplicationId(applicationId);
    }

    @Override
    public JobApplication createApplication(ApplicationRequest request, User user) {
        if (request == null) {
            throw new IllegalArgumentException("Job application cannot be null");
        }

        JobApplication application = mapJobApplication(request, user);

        return jobApplicationRepository.createApplication(application);
    }

    @Override
    public JobApplication updateApplication(JobApplication application) {
        if (application == null) {
            throw new IllegalArgumentException("Job application cannot be null");
        }

        if (application.getId() <= 0) {
            throw new IllegalArgumentException("Application ID must be greater than zero");
        }

        return jobApplicationRepository.updateApplication(application);
    }

    @Override
    public void deleteApplication(long applicationId) {
        if (applicationId <= 0) {
            throw new IllegalArgumentException("Application ID must be greater than zero");
        }

        jobApplicationRepository.deleteApplication(applicationId);
    }

    private JobApplication mapJobApplication(ApplicationRequest request, User user) {
        return JobApplication.builder()
                .jobTitle(request.jobTitle())
                .company(request.company())
                .dateApplied(request.dateApplied())
                .location(request.location())
                .portalUrl(request.portalUrl())
                .status(ApplicationStatus.APPLIED)
                .notes(request.notes())
                .userId(user.getId())
                .build();
    }
}
