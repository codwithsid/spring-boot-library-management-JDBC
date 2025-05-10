package com.librarymanagement.demo.repository;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.model.Author;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorRepository {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AuthorRepository.class);

    public Author save(Author author) {
        logger.info("Entering save");
        String sql = "INSERT INTO author (first_name, last_name, biography) VALUES (?, ?, ?)";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setString(3, author.getBiography());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating author failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    author.setAuthorId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating author failed, no ID obtained.");
                }
            }

            logger.info("Exiting save");
        } catch (SQLException e) {
            logger.error("Exception in save", e);
            throw new RuntimeException(e);
        }

        return author;
    }

    public Author findById(int id) {
        logger.info("Entering findById with ID: {}", id);
        String sql = "SELECT * FROM author WHERE author_id = ?";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                logger.info("Exiting findById");
                return mapResultSetToAuthor(rs);
            }

        } catch (SQLException e) {
            logger.error("Exception in findById", e);
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Author> findAll() {
        logger.info("Entering findAll");
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM author";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                authors.add(mapResultSetToAuthor(rs));
            }

            logger.info("Exiting findAll");
        } catch (SQLException e) {
            logger.error("Exception in findAll", e);
            throw new RuntimeException(e);
        }

        return authors;
    }

    public boolean existsById(int id) {
        logger.info("Entering existsById with ID: {}", id);
        String sql = "SELECT 1 FROM author WHERE author_id = ?";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            logger.info("Exiting existsById");
            return exists;

        } catch (SQLException e) {
            logger.error("Exception in existsById", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        logger.info("Entering deleteById with ID: {}", id);
        String sql = "DELETE FROM author WHERE author_id = ?";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Exiting deleteById");

        } catch (SQLException e) {
            logger.error("Exception in deleteById", e);
            throw new RuntimeException(e);
        }
    }

    public List<Author> findByNameLike(String name) {
        logger.info("Entering findByNameLike with name: {}", name);
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM author WHERE LOWER(first_name) LIKE ? OR LOWER(last_name) LIKE ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchTerm = "%" + name.toLowerCase() + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                authors.add(mapResultSetToAuthor(rs));
            }

            logger.info("Exiting findByNameLike");
        } catch (SQLException e) {
            logger.error("Exception in findByNameLike", e);
            throw new RuntimeException(e);
        }

        return authors;
    }

    private Author mapResultSetToAuthor(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setAuthorId(rs.getInt("author_id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));
        author.setBiography(rs.getString("biography"));
        return author;
    }
}
