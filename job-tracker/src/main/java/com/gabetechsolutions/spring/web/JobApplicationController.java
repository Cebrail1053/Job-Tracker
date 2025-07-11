package com.gabetechsolutions.spring.web;

import com.gabetechsolutions.spring.common.Path;
import com.gabetechsolutions.spring.service.JobApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Path.API_V1)
public class JobApplicationController {
    // TODO: Implement job application controller methods here
    private JobApplicationService applicationService;

    @GetMapping(Path.JOB_APPLICATION_URI)
    public ResponseEntity<?> getJobApplications() {
        return ResponseEntity.ok("it works!");
    }
}
