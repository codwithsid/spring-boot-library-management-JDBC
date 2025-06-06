package com.librarymanagement.demo.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtility {

    private static final String URL = "jdbc:mysql://localhost:3306/librarymanagementdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "tiger";

    // Private constructor to prevent instantiation
    private JDBCUtility() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
