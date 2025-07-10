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
    private static final String UPDATE_APPLICATION_SQL = "UPDATE " + TABLE_NAME + " SET job_title = " +
          ":jobTitle, company = :company, date_applied = :dateApplied, location = :location, portal_url = " +
          ":portalUrl, status = :status, notes = :notes WHERE job_id = :applicationId";
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
        MapSqlParameterSource parameters = new MapSqlParameterSource()
              .addValue("jobTitle", jobApplication.getJobTitle())
              .addValue("company", jobApplication.getCompany())
              .addValue("dateApplied", jobApplication.getDateApplied())
              .addValue("location", jobApplication.getLocation())
              .addValue("portalUrl", jobApplication.getPortalUrl())
              .addValue("status", jobApplication.getStatus().name())
              .addValue("notes", jobApplication.getNotes())
              .addValue("applicationId", jobApplication.getId());
        try {
            int rowsAffected = jdbcTemplate.update(UPDATE_APPLICATION_SQL, parameters);
            if (rowsAffected == 0) {
                throw new DataAccessResourceFailureException("Failed to update job application with ID: "
                      + jobApplication.getId());
            }
            return jobApplication;
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error updating job application with ID: "
                  + jobApplication.getId(), e);
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

    public static class JobApplicationRowMapper implements RowMapper<JobApplication> {

        @Override
        public JobApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                JobApplication jobApplication = new JobApplication();
                jobApplication.setId(rs.getLong("job_id"));
                jobApplication.setJobTitle(rs.getString("job_title"));
                jobApplication.setCompany(rs.getString("company"));
                jobApplication.setDateApplied(rs.getObject("date_applied", LocalDate.class));
                jobApplication.setLocation(rs.getString("location"));
                jobApplication.setPortalUrl(rs.getString("portal_url"));
                jobApplication.setStatus(ApplicationStatus.valueOf(rs.getString("status")));
                jobApplication.setNotes(rs.getString("notes"));
                jobApplication.setUserId(rs.getBytes("user_id"));
                return jobApplication;
            } catch (SQLException e) {
                throw new SQLException("Error mapping Job Application from ResultSet", e);
            }
        }
    }
}
