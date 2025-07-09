package com.gabetechsolutions.spring.repository.jdbc;

import com.gabetechsolutions.spring.domain.JobApplication;
import com.gabetechsolutions.spring.domain.enums.ApplicationStatus;
import com.gabetechsolutions.spring.repository.JobApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
          "company, " +
          "date_applied, location, portal_url, status, notes, user_id) VALUES (:jobTitle, :company, " +
          ":dateApplied, " +
          ":location, :portalUrl, :status, :notes, :userId)";
    //TODO: Potentially change the update SQL to include only the fields that are actually being updated
    private static final String UPDATE_APPLICATION_SQL = "UPDATE " + TABLE_NAME + " SET job_title = " +
          ":jobTitle, " +
          "company = :company, date_applied = :dateApplied, location = :location, portal_url = :portalUrl, " +
          "status = :status, notes = :notes WHERE job_id = :applicationId";
    private static final String DELETE_APPLICATION_SQL = "DELETE FROM " + TABLE_NAME + " WHERE job_id = " +
          ":applicationId";

    // TODO: Implement the methods to interact with the database
    @Override
    public List<JobApplication> findAllByUserId(byte[] userId) {
        return List.of();
    }

    @Override
    public JobApplication findByApplicationId(long applicationId) {
        return null;
    }

    @Override
    @Transactional
    public JobApplication createApplication(JobApplication jobApplication) {
        return null;
    }

    @Override
    @Transactional
    public JobApplication updateApplication(JobApplication jobApplication) {
        return null;
    }

    @Override
    @Transactional
    public void deleteApplication(long applicationId) {

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
