package com.gabetechsolutions.spring.repository;

import com.gabetechsolutions.spring.domain.Application;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository {

    List<Application> findAll();

    Application findById(int id);


}
