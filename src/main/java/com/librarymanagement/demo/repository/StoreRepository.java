package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Store;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StoreRepository {

    public Store save(Store store) {
        String sql = "INSERT INTO store (name, contact_number, email, address_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, store.getName());
            stmt.setString(2, store.getContactNumber());
            stmt.setString(3, store.getEmail());
            stmt.setInt(4, store.getStoreAddress().getAddressId()); // Assuming Address is already set

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
            e.printStackTrace();
        }

        return store;
    }

    public Store findById(int id) {
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
            e.printStackTrace();
        }

        return store;
    }

    public List<Store> findAll() {
        List<Store> stores = new ArrayList<>();
        String sql = "SELECT * FROM store";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                stores.add(mapResultSetToStore(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stores;
    }

    public Store update(Store store) {
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
            e.printStackTrace();
        }

        return store;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM store WHERE store_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Store mapResultSetToStore(ResultSet rs) throws SQLException {
        Store store = new Store();
        store.setStoreId(rs.getInt("store_id"));
        store.setName(rs.getString("name"));
        store.setContactNumber(rs.getString("contact_number"));
        store.setEmail(rs.getString("email"));
        // Assuming Address is handled separately
        return store;
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM store WHERE store_id = ?";
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
