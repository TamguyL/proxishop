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
     * @return a Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection establishConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    /**
     * Validates the input parameters to avoid SQL injection.
     *
     * @param databaseName the name of the database
     * @param classes      a list of classes to create tables for
     */
    public static void validateParameters(String databaseName, List<Class<?>> classes) {
        if (databaseName == null || databaseName.trim().isEmpty() || classes == null || classes.isEmpty()) {
            throw new IllegalArgumentException("Database name and classes must not be null or empty.");
        }
        if (!databaseName.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid database name.");
        }
    }
}
