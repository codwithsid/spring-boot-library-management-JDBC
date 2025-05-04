package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Billing;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BillingRepository {

    public Billing save(Billing billing) {
        String sql = "INSERT INTO billing (amount, billing_date, user_id) VALUES (?, ?, ?)";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, billing.getAmount());
            stmt.setTimestamp(2, Timestamp.valueOf(billing.getDate())); // Convert LocalDateTime to java.sql.Timestamp
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
            e.printStackTrace();
        }

        return billing;
    }

    public Billing findById(int id) {
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
            e.printStackTrace();
        }

        return billing;
    }

    public List<Billing> findAll() {
        List<Billing> billings = new ArrayList<>();
        String sql = "SELECT * FROM billing";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                billings.add(mapResultSetToBilling(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billings;
    }

    public Billing update(Billing billing) {
        String sql = "UPDATE billing SET amount = ?, billing_date = ?, user_id = ? WHERE billing_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, billing.getAmount());
            stmt.setTimestamp(2, Timestamp.valueOf(billing.getDate())); // Convert LocalDateTime to java.sql.Timestamp
            stmt.setInt(3, billing.getUser().getUserId());
            stmt.setInt(4, billing.getBillingId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billing;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM billing WHERE billing_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM billing WHERE billing_id = ?";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Billing mapResultSetToBilling(ResultSet rs) throws SQLException {
        Billing billing = new Billing();
        billing.setBillingId(rs.getInt("billing_id"));
        billing.setAmount(rs.getDouble("amount"));
        billing.setDate(rs.getTimestamp("billing_date").toLocalDateTime()); // Convert Timestamp to LocalDateTime
        // User can be fetched if needed
        return billing;
    }
}
