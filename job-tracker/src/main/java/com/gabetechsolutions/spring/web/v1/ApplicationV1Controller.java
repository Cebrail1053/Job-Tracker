package com.gabetechsolutions.spring.web.v1;

import com.gabetechsolutions.spring.client.ApplicationRequest;
import com.gabetechsolutions.spring.common.Path;
import com.gabetechsolutions.spring.domain.JobApplication;
import com.gabetechsolutions.spring.domain.User;
import com.gabetechsolutions.spring.service.JobApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Path.API_V1)
@AllArgsConstructor
public class ApplicationV1Controller {

    private final JobApplicationService applicationService;

    @GetMapping(Path.JOB_APPLICATION_URI)
    public ResponseEntity<?> getJobApplications() {
        User user = getCurrentUser();
        return ResponseEntity.ok(applicationService.getAllApplications(user.getId()));
    }

    @GetMapping(Path.JOB_APPLICATION_URI + "/{applicationId}")
    public ResponseEntity<?> getJobApplicationById(@PathVariable long applicationId) {
        return ResponseEntity.ok(applicationService.getApplicationById(applicationId));
    }

    @PostMapping(Path.JOB_APPLICATION_URI)
    public ResponseEntity<?> createJobApplication(@RequestBody ApplicationRequest request) {
        User user = getCurrentUser();
        applicationService.createApplication(request, user);
        return ResponseEntity.ok("Job application created successfully");
    }

    @PutMapping(Path.JOB_APPLICATION_URI + "/update")
    public ResponseEntity<?> updateJobApplication(@RequestBody JobApplication application) {
        JobApplication updatedApplication = applicationService.updateApplication(application);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping(Path.JOB_APPLICATION_URI + "/{applicationId}")
    public ResponseEntity<?> deleteJobApplication(@PathVariable long applicationId) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.ok("Job application deleted successfully");
    }

    protected User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
