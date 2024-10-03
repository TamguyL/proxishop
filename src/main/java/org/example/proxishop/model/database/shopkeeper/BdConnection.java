package org.example.proxishop.model.database.shopkeeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Manages the connection to the database.
 */
public class BdConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Establishes a connection to the database.
     *
     * @param website_name the name of the database
     * @return a Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection establishConnection(String website_name) throws SQLException {
        return DriverManager.getConnection(URL + website_name, USER, PASSWORD);
    }

    /**
     * Validates the input parameters to avoid SQL injection.
     *
     * @param website_name the name of the database
     * @param classes      a list of classes to create tables for
     */
    public static void validateParameters(String website_name, List<Class<?>> classes) {
        if (website_name == null || website_name.trim().isEmpty() || classes == null || classes.isEmpty()) {
            throw new IllegalArgumentException("Website name and classes must not be null or empty.");
        }
        if (!website_name.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid database name.");
        }
    }
}
