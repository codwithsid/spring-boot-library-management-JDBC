package com.librarymanagement.demo.repository;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.model.Transactions;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionsRepository {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(TransactionsRepository.class);

    public Transactions save(Transactions transaction) {
        logger.info("Entered save()");
        String sql = "INSERT INTO transactions (borrow_date, return_date, is_returned, user_id, book_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(transaction.getBorrowDate()));
            stmt.setTimestamp(2, Timestamp.valueOf(transaction.getReturnDate()));
            stmt.setBoolean(3, transaction.isReturned());
            stmt.setInt(4, transaction.getUser().getUserId());
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
            logger.error("Exception in save(): ", e);
        }

        logger.info("Exiting save()");
        return transaction;
    }

    public Transactions findById(int id) {
        logger.info("Entered findById() with id: {}", id);
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
            logger.error("Exception in findById(): ", e);
        }

        logger.info("Exiting findById()");
        return transaction;
    }

    public List<Transactions> findAll() {
        logger.info("Entered findAll()");
        List<Transactions> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }

        } catch (SQLException e) {
            logger.error("Exception in findAll(): ", e);
        }

        logger.info("Exiting findAll()");
        return transactions;
    }

    public Transactions update(Transactions transaction) {
        logger.info("Entered update() with id: {}", transaction.getTransactionId());
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
            logger.error("Exception in update(): ", e);
        }

        logger.info("Exiting update()");
        return transaction;
    }

    public void deleteById(int id) {
        logger.info("Entered deleteById() with id: {}", id);
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Exception in deleteById(): ", e);
        }

        logger.info("Exiting deleteById()");
    }

    private Transactions mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Transactions transaction = new Transactions();
        transaction.setTransactionId(rs.getInt("transaction_id"));
        transaction.setBorrowDate(rs.getTimestamp("borrow_date").toLocalDateTime());
        transaction.setReturnDate(rs.getTimestamp("return_date").toLocalDateTime());
        transaction.setReturned(rs.getBoolean("is_returned"));
        return transaction;
    }

    public boolean existsById(int id) {
        logger.info("Entered existsById() with id: {}", id);
        String sql = "SELECT 1 FROM transactions WHERE transaction_id = ?";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            logger.info("Exiting existsById() with result: {}", exists);
            return exists;

        } catch (SQLException e) {
            logger.error("Exception in existsById(): ", e);
        }

        return false;
    }
}
