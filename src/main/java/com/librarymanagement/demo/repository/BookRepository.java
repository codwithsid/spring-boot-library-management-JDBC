package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Book;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.springframework.stereotype.Repository;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BookRepository.class);

    public Book save(Book book) {
        logger.info("Entering save() with book: {}", book);
        String sql = "INSERT INTO book (title, isbn, publish_date, total_copies, available_copies, category, language, price, is_available, author_id, publisher_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getIsbn());
            stmt.setDate(3, Date.valueOf(book.getPublishDate()));
            stmt.setInt(4, book.getTotalCopies());
            stmt.setInt(5, book.getAvailableCopies());
            stmt.setString(6, book.getCategory());
            stmt.setString(7, book.getLanguage());
            stmt.setDouble(8, book.getPrice());
            stmt.setBoolean(9, book.isAvailable());
            stmt.setInt(10, book.getAuthor().getAuthorId());
            stmt.setInt(11, book.getPublisher().getPublisherId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setBookId(generatedKeys.getInt(1));
                    logger.info("Book saved successfully with ID: {}", book.getBookId());
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            logger.error("Error in save(): {}", e.getMessage(), e);
        }

        logger.info("Exiting save()");
        return book;
    }

    public Book findById(int id) {
        logger.info("Entering findById() with ID: {}", id);
        String sql = "SELECT * FROM book WHERE book_id = ?";
        Book book = null;

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                book = mapResultSetToBook(rs);
                logger.info("Book found with ID: {}", id);
            } else {
                logger.warn("No book found with ID: {}", id);
            }

        } catch (SQLException e) {
            logger.error("Error in findById(): {}", e.getMessage(), e);
        }

        logger.info("Exiting findById()");
        return book;
    }

    public List<Book> findAll() {
        logger.info("Entering findAll()");
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }

            logger.info("Total books retrieved: {}", books.size());

        } catch (SQLException e) {
            logger.error("Error in findAll(): {}", e.getMessage(), e);
        }

        logger.info("Exiting findAll()");
        return books;
    }

    public Book update(Book book) {
        logger.info("Entering update() for book ID: {}", book.getBookId());
        String sql = "UPDATE book SET title = ?, isbn = ?, publish_date = ?, total_copies = ?, available_copies = ?, category = ?, language = ?, price = ?, is_available = ?, author_id = ?, publisher_id = ? WHERE book_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getIsbn());
            stmt.setDate(3, Date.valueOf(book.getPublishDate()));
            stmt.setInt(4, book.getTotalCopies());
            stmt.setInt(5, book.getAvailableCopies());
            stmt.setString(6, book.getCategory());
            stmt.setString(7, book.getLanguage());
            stmt.setDouble(8, book.getPrice());
            stmt.setBoolean(9, book.isAvailable());
            stmt.setInt(10, book.getAuthor().getAuthorId());
            stmt.setInt(11, book.getPublisher().getPublisherId());
            stmt.setInt(12, book.getBookId());

            int rowsUpdated = stmt.executeUpdate();
            logger.info("Rows updated: {}", rowsUpdated);

        } catch (SQLException e) {
            logger.error("Error in update(): {}", e.getMessage(), e);
        }

        logger.info("Exiting update()");
        return book;
    }

    public void deleteById(int id) {
        logger.info("Entering deleteById() with ID: {}", id);
        String sql = "DELETE FROM book WHERE book_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            logger.info("Rows deleted: {}", rowsDeleted);

        } catch (SQLException e) {
            logger.error("Error in deleteById(): {}", e.getMessage(), e);
        }

        logger.info("Exiting deleteById()");
    }

    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setIsbn(rs.getString("isbn"));
        book.setPublishDate(rs.getDate("publish_date").toLocalDate());
        book.setTotalCopies(rs.getInt("total_copies"));
        book.setAvailableCopies(rs.getInt("available_copies"));
        book.setCategory(rs.getString("category"));
        book.setLanguage(rs.getString("language"));
        book.setPrice(rs.getDouble("price"));
        book.setAvailable(rs.getBoolean("is_available"));
        return book;
    }

    public boolean existsById(int id) {
        logger.info("Entering existsById() with ID: {}", id);
        String sql = "SELECT 1 FROM book WHERE book_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            logger.info("Book exists: {}", exists);
            return exists;

        } catch (SQLException e) {
            logger.error("Error in existsById(): {}", e.getMessage(), e);
        }

        logger.info("Exiting existsById()");
        return false;
    }
}

