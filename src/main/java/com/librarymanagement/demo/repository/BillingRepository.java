package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Billing;
import com.librarymanagement.demo.utility.JDBCUtility;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BillingRepository {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BillingRepository.class);

    public Billing save(Billing billing) {
        logger.info("Entering save()");
        String sql = "INSERT INTO billing (amount, billing_date, user_id) VALUES (?, ?, ?)";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, billing.getAmount());
            stmt.setTimestamp(2, Timestamp.valueOf(billing.getDate()));
            stmt.setInt(3, billing.getUser().getUserId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating billing failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    billing.setBillingId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating billing failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            logger.error("Exception in save()", e);
            throw new RuntimeException(e);
        }

        logger.info("Exiting save()");
        return billing;
    }

    public Billing findById(int id) {
        logger.info("Entering findById() with id={}", id);
        String sql = "SELECT * FROM billing WHERE billing_id = ?";
        Billing billing = null;

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                billing = mapResultSetToBilling(rs);
            }

        } catch (SQLException e) {
            logger.error("Exception in findById()", e);
            throw new RuntimeException(e);
        }

        logger.info("Exiting findById()");
        return billing;
    }

    public List<Billing> findAll() {
        logger.info("Entering findAll()");
        List<Billing> billings = new ArrayList<>();
        String sql = "SELECT * FROM billing";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                billings.add(mapResultSetToBilling(rs));
            }

        } catch (SQLException e) {
            logger.error("Exception in findAll()", e);
            throw new RuntimeException(e);
        }

        logger.info("Exiting findAll()");
        return billings;
    }

    public Billing update(Billing billing) {
        logger.info("Entering update() with id={}", billing.getBillingId());
        String sql = "UPDATE billing SET amount = ?, billing_date = ?, user_id = ? WHERE billing_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, billing.getAmount());
            stmt.setTimestamp(2, Timestamp.valueOf(billing.getDate()));
            stmt.setInt(3, billing.getUser().getUserId());
            stmt.setInt(4, billing.getBillingId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Exception in update()", e);
            throw new RuntimeException(e);
        }

        logger.info("Exiting update()");
        return billing;
    }

    public void deleteById(int id) {
        logger.info("Entering deleteById() with id={}", id);
        String sql = "DELETE FROM billing WHERE billing_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Exception in deleteById()", e);
            throw new RuntimeException(e);
        }

        logger.info("Exiting deleteById()");
    }

    public boolean existsById(int id) {
        logger.info("Entering existsById() with id={}", id);
        String sql = "SELECT 1 FROM billing WHERE billing_id = ?";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            logger.info("Exiting existsById()");
            return exists;

        } catch (SQLException e) {
            logger.error("Exception in existsById()", e);
            throw new RuntimeException(e);
        }
    }

    private Billing mapResultSetToBilling(ResultSet rs) throws SQLException {
        Billing billing = new Billing();
        billing.setBillingId(rs.getInt("billing_id"));
        billing.setAmount(rs.getDouble("amount"));
        billing.setDate(rs.getTimestamp("billing_date").toLocalDateTime());
        return billing;
    }
}
