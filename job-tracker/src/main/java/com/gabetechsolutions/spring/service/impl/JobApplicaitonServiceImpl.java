package com.gabetechsolutions.spring.service.impl;

import com.gabetechsolutions.spring.domain.JobApplication;
import com.gabetechsolutions.spring.service.JobApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobApplicaitonServiceImpl implements JobApplicationService {

    //TODO: Finish implementing the JobApplicationService methods
    @Override
    public List<JobApplication> getAllApplications(byte[] userId) {
        return List.of();
    }

    @Override
    public JobApplication getApplicationById(long applicationId) {
        return null;
    }

    @Override
    public JobApplication createApplication(JobApplication application) {
        return null;
    }

    @Override
    public JobApplication updateApplication(JobApplication application) {
        return null;
    }

    @Override
    public void deleteApplication(long applicationId) {

    }
}
