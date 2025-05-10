package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.BookPublisher;
import com.librarymanagement.demo.utility.JDBCUtility;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookPublisherRepository {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BookPublisherRepository.class);

    public BookPublisher save(BookPublisher publisher) {
        logger.info("Entered save");
        String sql = "INSERT INTO book_publisher (name, contact_number, website, address_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, publisher.getName());
            stmt.setString(2, publisher.getContactNumber());
            stmt.setString(3, publisher.getWebsite());
            stmt.setInt(4, publisher.getAddress().getAddressId());

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

            logger.info("Exiting save");
        } catch (SQLException e) {
            logger.error("Exception in save: {}", e.getMessage(), e);
        }

        return publisher;
    }

    public BookPublisher findById(int id) {
        logger.info("Entered findById with id={}", id);
        String sql = "SELECT * FROM book_publisher WHERE publisher_id = ?";
        BookPublisher publisher = null;

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                publisher = mapResultSetToPublisher(rs);
            }

            logger.info("Exiting findById");
        } catch (SQLException e) {
            logger.error("Exception in findById: {}", e.getMessage(), e);
        }

        return publisher;
    }

    public List<BookPublisher> findAll() {
        logger.info("Entered findAll");
        List<BookPublisher> publishers = new ArrayList<>();
        String sql = "SELECT * FROM book_publisher";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                publishers.add(mapResultSetToPublisher(rs));
            }

            logger.info("Exiting findAll");
        } catch (SQLException e) {
            logger.error("Exception in findAll: {}", e.getMessage(), e);
        }

        return publishers;
    }

    public BookPublisher update(BookPublisher publisher) {
        logger.info("Entered update");
        String sql = "UPDATE book_publisher SET name = ?, contact_number = ?, website = ?, address_id = ? WHERE publisher_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, publisher.getName());
            stmt.setString(2, publisher.getContactNumber());
            stmt.setString(3, publisher.getWebsite());
            stmt.setInt(4, publisher.getAddress().getAddressId());
            stmt.setInt(5, publisher.getPublisherId());

            stmt.executeUpdate();
            logger.info("Exiting update");
        } catch (SQLException e) {
            logger.error("Exception in update: {}", e.getMessage(), e);
        }

        return publisher;
    }

    public void deleteById(int id) {
        logger.info("Entered deleteById with id={}", id);
        String sql = "DELETE FROM book_publisher WHERE publisher_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Exiting deleteById");
        } catch (SQLException e) {
            logger.error("Exception in deleteById: {}", e.getMessage(), e);
        }
    }

    public boolean existsById(int id) {
        logger.info("Entered existsById with id={}", id);
        String sql = "SELECT 1 FROM book_publisher WHERE publisher_id = ?";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            logger.info("Exiting existsById");
            return rs.next();

        } catch (SQLException e) {
            logger.error("Exception in existsById: {}", e.getMessage(), e);
        }
        return false;
    }

    private BookPublisher mapResultSetToPublisher(ResultSet rs) throws SQLException {
        BookPublisher publisher = new BookPublisher();
        publisher.setPublisherId(rs.getInt("publisher_id"));
        publisher.setName(rs.getString("name"));
        publisher.setContactNumber(rs.getString("contact_number"));
        publisher.setWebsite(rs.getString("website"));
        return publisher;
    }
}
