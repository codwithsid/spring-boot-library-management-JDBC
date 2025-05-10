package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Address;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AddressRepository {

    private static final Logger logger = LoggerFactory.getLogger(AddressRepository.class);

    public Address save(Address address) {
        logger.info("Saving new address to the database...");
        String sql = "INSERT INTO address (street, city, state, country, postal_code, landmark, address_type, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set params
            stmt.setString(1, address.getStreet());
            stmt.setString(2, address.getCity());
            stmt.setString(3, address.getState());
            stmt.setString(4, address.getCountry());
            stmt.setString(5, address.getPostalCode());
            stmt.setString(6, address.getLandmark());
            stmt.setString(7, address.getAddressType());
            stmt.setInt(8, address.getUser().getUserId());

            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    address.setAddressId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            logger.error("Error saving address: {}", e.getMessage(), e);
        }

        return address;
    }

    public Address findById(int id) {
        logger.info("Finding address by ID: {}", id);
        String sql = "SELECT * FROM address WHERE address_id = ?";
        Address address = null;

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                address = mapResultSetToAddress(rs);
            }

        } catch (SQLException e) {
            logger.error("Error fetching address: {}", e.getMessage(), e);
        }

        return address;
    }

    public List<Address> findAll() {
        logger.info("Fetching all addresses...");
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM address";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                addresses.add(mapResultSetToAddress(rs));
            }

        } catch (SQLException e) {
            logger.error("Error fetching all addresses: {}", e.getMessage(), e);
        }

        return addresses;
    }

    public Address update(int id, Address updatedAddress) {
        logger.info("Updating address with ID: {}", id);
        String sql = "UPDATE address SET street = ?, city = ?, state = ?, country = ?, postal_code = ?, landmark = ?, address_type = ?, user_id = ? " +
                "WHERE address_id = ?";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, updatedAddress.getStreet());
            stmt.setString(2, updatedAddress.getCity());
            stmt.setString(3, updatedAddress.getState());
            stmt.setString(4, updatedAddress.getCountry());
            stmt.setString(5, updatedAddress.getPostalCode());
            stmt.setString(6, updatedAddress.getLandmark());
            stmt.setString(7, updatedAddress.getAddressType());
            stmt.setInt(8, updatedAddress.getUser().getUserId());
            stmt.setInt(9, id);

            stmt.executeUpdate();
            updatedAddress.setAddressId(id);

        } catch (SQLException e) {
            logger.error("Error updating address: {}", e.getMessage(), e);
        }

        return updatedAddress;
    }

    public void deleteById(int id) {
        logger.info("Deleting address with ID: {}", id);
        String sql = "DELETE FROM address WHERE address_id = ?";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error deleting address: {}", e.getMessage(), e);
        }
    }

    public boolean existsById(int id) {
        logger.info("Checking existence of address with ID: {}", id);
        String sql = "SELECT 1 FROM address WHERE address_id = ?";
        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            logger.error("Error checking address existence: {}", e.getMessage(), e);
        }
        return false;
    }

    private Address mapResultSetToAddress(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setAddressId(rs.getInt("address_id"));
        address.setStreet(rs.getString("street"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setCountry(rs.getString("country"));
        address.setPostalCode(rs.getString("postal_code"));
        address.setLandmark(rs.getString("landmark"));
        address.setAddressType(rs.getString("address_type"));
        return address;
    }
}
