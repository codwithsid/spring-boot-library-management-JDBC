package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.User;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    public User save(User user) {
        String sql = "INSERT INTO users (first_name, last_name, email_id, password, mobile_number, dob, created_at, is_active, role, profile_image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmailId());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getMobileNumber());
            stmt.setDate(6, Date.valueOf(user.getDob()));
            stmt.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));
            stmt.setBoolean(8, user.isActive());
            stmt.setString(9, user.getRole());
            stmt.setString(10, user.getProfileImageUrl());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User findById(int id) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        User user = null;

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User update(User user) {
        String sql = "UPDATE user SET first_name = ?, last_name = ?, email_id = ?, mobile_number = ?, role = ?, profile_image_url = ? WHERE user_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmailId());
            stmt.setString(4, user.getMobileNumber());
            stmt.setString(5, user.getRole());
            stmt.setString(6, user.getProfileImageUrl());
            stmt.setInt(7, user.getUserId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM user WHERE user_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmailId(rs.getString("email_id"));
        user.setPassword(rs.getString("password"));
        user.setMobileNumber(rs.getString("mobile_number"));
        user.setDob(rs.getDate("dob").toLocalDate());
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setActive(rs.getBoolean("is_active"));
        user.setRole(rs.getString("role"));
        user.setProfileImageUrl(rs.getString("profile_image_url"));
        return user;
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM user WHERE user_id = ?";
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

    public User findByEmailId(String emailId) {
        String sql = "SELECT * FROM user WHERE email_id = ?";
        User user = null;

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emailId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User findByMobileNumber(String mobileNumber) {
        String sql = "SELECT * FROM user WHERE mobile_number = ?";
        User user = null;

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mobileNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
