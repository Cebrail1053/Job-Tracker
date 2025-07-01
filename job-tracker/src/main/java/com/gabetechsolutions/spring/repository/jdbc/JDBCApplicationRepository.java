package com.gabetechsolutions.spring.repository.jdbc;

import com.gabetechsolutions.spring.domain.Application;
import com.gabetechsolutions.spring.domain.Status;
import com.gabetechsolutions.spring.repository.ApplicationRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCApplicationRepository implements ApplicationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "APPLICATION";
    private static final String SELECT_AUTO_INCREMENT_SQL = "SELECT AUTO_INCREMENT FROM information_schema" +
          ".tables WHERE table_schema = jobtrackerdb AND table_name = " + TABLE_NAME;
    private static final String SELECT_APPLICATIONS_BY_USER_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " +
          "user_id = :user_id";
    private static final String SELECT_APPLICAATION_BY_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " +
          "job_id = :job_id";
    private static final String INSERT_APPLICATION_SQL = "INSERT INTO " + TABLE_NAME + " (job_id, " +
          "job_title, company, date_applied, location, portal_url, status, notes, user_id) VALUES (:job_id," +
          " :job_title, :company, :date_applied, :location, :portal_url, :status, :notes, :user_id)";
    private static final String DELETE_APPLICATION_SQL = "DELETE FROM " + TABLE_NAME + " WHERE job_id = " +
          ":job_id";

    public JDBCApplicationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Application> findByUser(String userId) {
        return List.of();
    }

    @Override
    public Optional<Application> findByJobId(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Application> createApplication(Application application) {
        return Optional.empty();
    }

    @Override
    public boolean updateApplication(Application application) {
        return false;
    }

    @Override
    public boolean deleteApplication(long id) {
        return false;
    }

    public static class ApplicationRowMapper implements RowMapper<Application> {

        @Override
        public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
            Application application = new Application();

            Date date = rs.getDate("date_applied");
            application.setDateApplied(rs.wasNull() ? Optional.empty() : Optional.of(date.toLocalDate()));
            application.setJobId(rs.getLong("job_id"));
            application.setJobTitle(rs.getString("job_title"));
            application.setCompany(rs.getString("company"));
            application.setLocation(Optional.ofNullable(rs.getString("location")));
            application.setPortalUrl(Optional.ofNullable(rs.getString("portal_url")));
            application.setStatus(Status.fromCode(rs.getInt("status")));
            application.setNotes(Optional.ofNullable(rs.getString("notes")));
            application.setUserId(rs.getString("user_id"));

            return application;
        }
    }
}
