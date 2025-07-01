package com.gabetechsolutions.spring.repository;

import com.gabetechsolutions.spring.domain.Application;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository {

    List<Application> findByUser(String userId);

    Optional<Application> findByJobId(long id);

    Optional<Application> createApplication(Application application);

    boolean updateApplication(Application application);

    boolean deleteApplication(long id);
}
