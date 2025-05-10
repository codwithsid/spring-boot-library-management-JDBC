package com.librarymanagement.demo.repository;

import ch.qos.logback.classic.Logger;
import com.librarymanagement.demo.model.Store;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StoreRepository {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(StoreRepository.class);

    public Store save(Store store) {
        logger.info("🔽 Entered save()");
        String sql = "INSERT INTO store (name, contact_number, email, address_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, store.getName());
            stmt.setString(2, store.getContactNumber());
            stmt.setString(3, store.getEmail());
            stmt.setInt(4, store.getStoreAddress().getAddressId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating store failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    store.setStoreId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating store failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            logger.error("❌ SQLException in save(): {}", e.getMessage());
        }

        logger.info("✅ Exiting save()");
        return store;
    }

    public Store findById(int id) {
        logger.info("🔽 Entered findById() with id: {}", id);
        String sql = "SELECT * FROM store WHERE store_id = ?";
        Store store = null;

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                store = mapResultSetToStore(rs);
            }

        } catch (SQLException e) {
            logger.error("❌ SQLException in findById(): {}", e.getMessage());
        }

        logger.info("✅ Exiting findById()");
        return store;
    }

    public List<Store> findAll() {
        logger.info("🔽 Entered findAll()");
        List<Store> stores = new ArrayList<>();
        String sql = "SELECT * FROM store";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                stores.add(mapResultSetToStore(rs));
            }

        } catch (SQLException e) {
            logger.error("❌ SQLException in findAll(): {}", e.getMessage());
        }

        logger.info("✅ Exiting findAll()");
        return stores;
    }

    public Store update(Store store) {
        logger.info("🔽 Entered update() with id: {}", store.getStoreId());
        String sql = "UPDATE store SET name = ?, contact_number = ?, email = ?, address_id = ? WHERE store_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, store.getName());
            stmt.setString(2, store.getContactNumber());
            stmt.setString(3, store.getEmail());
            stmt.setInt(4, store.getStoreAddress().getAddressId());
            stmt.setInt(5, store.getStoreId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("❌ SQLException in update(): {}", e.getMessage());
        }

        logger.info("✅ Exiting update()");
        return store;
    }

    public void deleteById(int id) {
        logger.info("🔽 Entered deleteById() with id: {}", id);
        String sql = "DELETE FROM store WHERE store_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("❌ SQLException in deleteById(): {}", e.getMessage());
        }

        logger.info("✅ Exiting deleteById()");
    }

    public boolean existsById(int id) {
        logger.info("🔽 Entered existsById() with id: {}", id);
        String sql = "SELECT 1 FROM store WHERE store_id = ?";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            logger.info("✅ Exiting existsById() with result: {}", exists);
            return exists;

        } catch (SQLException e) {
            logger.error("❌ SQLException in existsById(): {}", e.getMessage());
        }

        logger.info("✅ Exiting existsById() with result: false");
        return false;
    }

    private Store mapResultSetToStore(ResultSet rs) throws SQLException {
        Store store = new Store();
        store.setStoreId(rs.getInt("store_id"));
        store.setName(rs.getString("name"));
        store.setContactNumber(rs.getString("contact_number"));
        store.setEmail(rs.getString("email"));
        return store;
    }
}
