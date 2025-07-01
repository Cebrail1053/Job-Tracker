package com.gabetechsolutions.spring.repository.jdbc;

import com.gabetechsolutions.spring.domain.Application;
import com.gabetechsolutions.spring.repository.ApplicationRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JDBCApplicationRepository implements ApplicationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "APPLICATION";
    private static final String SELECT_AUTO_INCREMENT_SQL = "SELECT AUTO_INCREMENT FROM information_schema" +
          ".tables WHERE table_schema = jobtrackerdb AND table_name = " + TABLE_NAME;
    private static final String SELECT_APPLICATIONS_BY_USER_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " +
          "user_id = :user_id";
    private static final String INSERT_APPLICATION_SQL = "INSERT INTO " + TABLE_NAME + " (job_id, " +
          "job_title, company, date_applied, location, portal_url, status, notes, user_id) VALUES (:job_id," +
          " :job_title, :company, :date_applied, :location, :portal_url, :status, :notes, :user_id)";

    private MapSqlParameterSource columnsAndParams = new MapSqlParameterSource().addValues(Map.of(
          "job_id", ":job_id"
    ));


    public JDBCApplicationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Application> findByUser(String id) {
        return List.of();
    }

    @Override
    public Optional<Application> findByJobId(long id) {
        return Optional.empty();
    }
}
