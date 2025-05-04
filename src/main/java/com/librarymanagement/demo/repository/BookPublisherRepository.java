package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.BookPublisher;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookPublisherRepository {

    public BookPublisher save(BookPublisher publisher) {
        String sql = "INSERT INTO book_publisher (name, contact_number, website, address_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, publisher.getName());
            stmt.setString(2, publisher.getContactNumber());
            stmt.setString(3, publisher.getWebsite());
            stmt.setInt(4, publisher.getAddress().getAddressId()); // Assuming address is already set

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating publisher failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    publisher.setPublisherId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating publisher failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publisher;
    }

    public BookPublisher findById(int id) {
        String sql = "SELECT * FROM book_publisher WHERE publisher_id = ?";
        BookPublisher publisher = null;

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                publisher = mapResultSetToPublisher(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publisher;
    }

    public List<BookPublisher> findAll() {
        List<BookPublisher> publishers = new ArrayList<>();
        String sql = "SELECT * FROM book_publisher";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                publishers.add(mapResultSetToPublisher(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publishers;
    }

    public BookPublisher update(BookPublisher publisher) {
        String sql = "UPDATE book_publisher SET name = ?, contact_number = ?, website = ?, address_id = ? WHERE publisher_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, publisher.getName());
            stmt.setString(2, publisher.getContactNumber());
            stmt.setString(3, publisher.getWebsite());
            stmt.setInt(4, publisher.getAddress().getAddressId());
            stmt.setInt(5, publisher.getPublisherId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publisher;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM book_publisher WHERE publisher_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private BookPublisher mapResultSetToPublisher(ResultSet rs) throws SQLException {
        BookPublisher publisher = new BookPublisher();
        publisher.setPublisherId(rs.getInt("publisher_id"));
        publisher.setName(rs.getString("name"));
        publisher.setContactNumber(rs.getString("contact_number"));
        publisher.setWebsite(rs.getString("website"));
        // Assuming Address is set through another method if needed
        return publisher;
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM book_publisher WHERE publisher_id = ?";
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
}
