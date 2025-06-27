package com.gabetechsolutions.spring.repository.jdbc;

import com.gabetechsolutions.spring.domain.Application;
import com.gabetechsolutions.spring.repository.ApplicationRepository;

import java.util.List;

public class JDBCApplicationRepository implements ApplicationRepository {
    @Override
    public List<Application> findAll() {
        return List.of();
    }

    @Override
    public Application findById(int id) {
        return null;
    }
}
