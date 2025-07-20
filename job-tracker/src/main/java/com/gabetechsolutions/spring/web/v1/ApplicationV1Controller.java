package com.gabetechsolutions.spring.web.v1;

import com.gabetechsolutions.spring.common.Path;
import com.gabetechsolutions.spring.service.JobApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Path.API_V1)
@AllArgsConstructor
public class ApplicationV1Controller {
    // TODO: Implement job application controller methods here
    private final JobApplicationService applicationService;

    @GetMapping(Path.JOB_APPLICATION_URI)
    public ResponseEntity<?> getJobApplications() {
        return ResponseEntity.ok("it works!");
    }
}
