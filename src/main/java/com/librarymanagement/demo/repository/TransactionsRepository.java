package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Transactions;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionsRepository {

    public Transactions save(Transactions transaction) {
        String sql = "INSERT INTO transactions (borrow_date, return_date, is_returned, user_id, book_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(transaction.getBorrowDate()));
            stmt.setTimestamp(2, Timestamp.valueOf(transaction.getReturnDate()));
            stmt.setBoolean(3, transaction.isReturned());
            stmt.setInt(4, transaction.getUser().getUserId()); // Assuming User and Book are set already
            stmt.setInt(5, transaction.getBook().getBookId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating transaction failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaction.setTransactionId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating transaction failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public Transactions findById(int id) {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        Transactions transaction = null;

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                transaction = mapResultSetToTransaction(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public List<Transactions> findAll() {
        List<Transactions> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public Transactions update(Transactions transaction) {
        String sql = "UPDATE transactions SET borrow_date = ?, return_date = ?, is_returned = ?, user_id = ?, book_id = ? WHERE transaction_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(transaction.getBorrowDate()));
            stmt.setTimestamp(2, Timestamp.valueOf(transaction.getReturnDate()));
            stmt.setBoolean(3, transaction.isReturned());
            stmt.setInt(4, transaction.getUser().getUserId());
            stmt.setInt(5, transaction.getBook().getBookId());
            stmt.setInt(6, transaction.getTransactionId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Transactions mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Transactions transaction = new Transactions();
        transaction.setTransactionId(rs.getInt("transaction_id"));
        transaction.setBorrowDate(rs.getTimestamp("borrow_date").toLocalDateTime());
        transaction.setReturnDate(rs.getTimestamp("return_date").toLocalDateTime());
        transaction.setReturned(rs.getBoolean("is_returned"));
        // Assuming User and Book are set through another method if needed
        return transaction;
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM transactions WHERE transaction_id = ?";
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
