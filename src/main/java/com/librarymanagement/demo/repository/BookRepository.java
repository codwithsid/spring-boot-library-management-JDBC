package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Book;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {

    public Book save(Book book) {
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
            stmt.setInt(10, book.getAuthor().getAuthorId());  // Assuming Author and Publisher are set already
            stmt.setInt(11, book.getPublisher().getPublisherId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setBookId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    public Book findById(int id) {
        String sql = "SELECT * FROM book WHERE book_id = ?";
        Book book = null;

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                book = mapResultSetToBook(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public Book update(Book book) {
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

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM book WHERE book_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        // Assuming Author and Publisher are set through another method if needed
        return book;
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM book WHERE book_id = ?";
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
