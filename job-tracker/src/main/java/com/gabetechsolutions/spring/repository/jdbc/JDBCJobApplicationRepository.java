package com.gabetechsolutions.spring.repository.jdbc;

import com.gabetechsolutions.spring.domain.JobApplication;
import com.gabetechsolutions.spring.domain.enums.ApplicationStatus;
import com.gabetechsolutions.spring.repository.JobApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@AllArgsConstructor
public class JDBCJobApplicationRepository implements JobApplicationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String TABLE_NAME = "application";
    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = " +
          ":userId";
    private static final String FIND_BY_APPLICATION_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE job_id" +
          " = :applicationId";
    private static final String CREATE_APPLICATION_SQL = "INSERT INTO " + TABLE_NAME + " (job_title, " +
          "company, date_applied, location, portal_url, status, notes, user_id) VALUES (:jobTitle, " +
          ":company, :dateApplied, :location, :portalUrl, :status, :notes, :userId)";
    private static final String DELETE_APPLICATION_SQL = "DELETE FROM " + TABLE_NAME + " WHERE job_id = " +
          ":applicationId";

    @Override
    public List<JobApplication> findAllByUserId(byte[] userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
              .addValue("userId", userId);
        try {
            return jdbcTemplate.query(FIND_BY_USER_ID_SQL, parameters, new JobApplicationRowMapper());
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error finding job applications for user ID: "
                  + Arrays.toString(userId), e);
        }
    }

    @Override
    public JobApplication findByApplicationId(long applicationId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
              .addValue("applicationId", applicationId);
        try {
            return jdbcTemplate.query(FIND_BY_APPLICATION_ID_SQL, parameters,
                  new JobApplicationRowMapper()).getFirst();
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error finding job application by ID: " + applicationId
                  , e);
        }
    }

    @Override
    @Transactional
    public JobApplication createApplication(JobApplication jobApplication) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
              .addValue("jobTitle", jobApplication.getJobTitle())
              .addValue("company", jobApplication.getCompany())
              .addValue("dateApplied", jobApplication.getDateApplied())
              .addValue("location", jobApplication.getLocation())
              .addValue("portalUrl", jobApplication.getPortalUrl())
              .addValue("status", jobApplication.getStatus().name())
              .addValue("notes", jobApplication.getNotes())
              .addValue("userId", jobApplication.getUserId());
        try {
            int rowsAffected = jdbcTemplate.update(CREATE_APPLICATION_SQL, parameters);
            if (rowsAffected == 0) {
                throw new DataAccessResourceFailureException("Failed to create job application for user ID: "
                      + Arrays.toString(jobApplication.getUserId()));
            }
            return jobApplication;
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error creating job application for user ID: "
                  + Arrays.toString(jobApplication.getUserId()), e);
        }
    }

    @Override
    @Transactional
    public JobApplication updateApplication(JobApplication jobApplication) {
        List<String> updates = new ArrayList<>();
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        addParameterIfNotNull(updates, parameters, "job_title", "jobTitle", jobApplication.getJobTitle());
        addParameterIfNotNull(updates, parameters, "company", "company", jobApplication.getCompany());
        addParameterIfNotNull(updates, parameters, "date_applied", "dateApplied",
              jobApplication.getDateApplied());
        addParameterIfNotNull(updates, parameters, "location", "location", jobApplication.getLocation());
        addParameterIfNotNull(updates, parameters, "portal_url", "portalUrl", jobApplication.getPortalUrl());
        addParameterIfNotNull(updates, parameters, "status", "status", jobApplication.getStatus().name());
        addParameterIfNotNull(updates, parameters, "notes", "notes", jobApplication.getNotes());

        if (updates.isEmpty()) {
            return jobApplication;
        }

        String sql = String.format("UPDATE %s SET %s WHERE job_id = :applicationId",
              TABLE_NAME,
              String.join(", ", updates));

        parameters.addValue("applicationId", jobApplication.getId());

        try {
            int rowsAffected = jdbcTemplate.update(sql, parameters);
            if (rowsAffected == 0) {
                throw new DataAccessResourceFailureException("Failed to update job application with ID: " + jobApplication.getId());
            }
            return jobApplication;
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error updating job application with ID: " + jobApplication.getId(), e);
        }
    }

    @Override
    @Transactional
    public void deleteApplication(long applicationId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
              .addValue("applicationId", applicationId);
        try {
            int rowsAffected = jdbcTemplate.update(DELETE_APPLICATION_SQL, parameters);
            if (rowsAffected == 0) {
                throw new DataAccessResourceFailureException("Failed to delete job application with ID: "
                      + applicationId);
            }
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error deleting job application with ID: "
                  + applicationId, e);
        }
    }

    private void addParameterIfNotNull(List<String> updates, MapSqlParameterSource parameters,
                                       String columnName, String paramName, Object value) {
        if (value != null) {
            updates.add(columnName + " = :" + paramName);
            parameters.addValue(paramName, value);
        }
    }

    public static class JobApplicationRowMapper implements RowMapper<JobApplication> {

        @Override
        public JobApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                return JobApplication.builder()
                      .id(rs.getLong("job_id"))
                      .jobTitle(rs.getString("job_title"))
                      .company(rs.getString("company"))
                      .dateApplied(rs.getObject("date_applied", LocalDate.class))
                      .location(rs.getString("location"))
                      .portalUrl(rs.getString("portal_url"))
                      .status(ApplicationStatus.valueOf(rs.getString("status")))
                      .notes(rs.getString("notes"))
                      .userId(rs.getBytes("user_id"))
                      .build();
            } catch (SQLException e) {
                throw new SQLException("Error mapping Job Application from ResultSet", e);
            }
        }
    }
}
