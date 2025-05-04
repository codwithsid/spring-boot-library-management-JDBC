package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Address;
import com.librarymanagement.demo.utility.JDBCUtility;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AddressRepository {

    public Address save(Address address) {
        String sql = "INSERT INTO address (street, city, state, country, postal_code, landmark, address_type, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, address.getStreet());
            stmt.setString(2, address.getCity());
            stmt.setString(3, address.getState());
            stmt.setString(4, address.getCountry());
            stmt.setString(5, address.getPostalCode());
            stmt.setString(6, address.getLandmark());
            stmt.setString(7, address.getAddressType());
            stmt.setInt(8, address.getUser().getUserId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating address failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    address.setAddressId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating address failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return address;
    }

    public Address findById(int id) {
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
            e.printStackTrace();
        }

        return address;
    }

    public List<Address> findAll() {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM address";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                addresses.add(mapResultSetToAddress(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    public Address update(int id, Address updatedAddress) {
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
            e.printStackTrace();
        }

        return updatedAddress;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM address WHERE address_id = ?";

        try (Connection conn = JDBCUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM address WHERE address_id = ?";
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
