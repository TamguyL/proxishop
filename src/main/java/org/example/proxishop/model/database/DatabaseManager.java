package org.example.proxishop.model.database;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public void createDatabaseAndTables(String databaseName, List<Class<?>> classes, List shopkeeper) {
        try {
            validateParameters(databaseName, classes);
            Connection connection = establishConnection();
            createDatabaseAndTables(connection, databaseName, classes);
            insertShopkeeperData(connection, shopkeeper);
        } catch (SQLException e) {
            System.err.println("SQL error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }

    private void validateParameters(String databaseName, List<Class<?>> classes) {
        if (databaseName == null || databaseName.trim().isEmpty() || classes == null || classes.isEmpty()) {
            throw new IllegalArgumentException("Database name and classes must not be null or empty.");
        }
        if (!databaseName.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid database name.");
        }
    }

    private Connection establishConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void createDatabaseAndTables(Connection connection, String databaseName, List<Class<?>> classes) throws SQLException {
        Statement statement = connection.createStatement();

        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        statement.executeUpdate(createDatabaseSQL);
        System.out.println("Database " + databaseName + " created successfully.");

        String useDatabaseSQL = "USE " + databaseName;
        statement.executeUpdate(useDatabaseSQL);

        List<String> createTableSQLs = generateCreateTableSQL(classes);
        for (String createTableSQL : createTableSQLs) {
            statement.executeUpdate(createTableSQL);
        }
        System.out.println("All Tables created successfully.");
    }

    private static List<String> generateCreateTableSQL(List<Class<?>> classes) {
        List<String> sqlStatements = new ArrayList<>();
        for (Class<?> clazz : classes) {
            StringBuilder sql = new StringBuilder();
            sql.append("CREATE TABLE IF NOT EXISTS ").append(clazz.getSimpleName()).append(" (");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                sql.append(field.getName()).append(" ");
                if (field.getType().equals(Double.class)) {
                    sql.append("DOUBLE");
                } else if (field.getType().equals(String.class)) {
                    sql.append("VARCHAR(255)");
                } else {
                    throw new IllegalArgumentException("Type non supporté: " + field.getType());
                }
                if (field.getName().equals("id")) {
                    sql.append(" PRIMARY KEY AUTO_INCREMENT");
                }
                sql.append(", ");
            }
            sql.setLength(sql.length() - 2);
            sql.append(")");
            sqlStatements.add(sql.toString());
        }
        return sqlStatements;
    }

    private void insertShopkeeperData(Connection connection, List shopkeeper) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO shopkeeper (siret, firstName, lastName, email, adress, profilePicture) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setDouble(1, (Double) shopkeeper.get(0)); // siret
        preparedStatement.setString(2, (String) shopkeeper.get(1)); // firstName
        preparedStatement.setString(3, (String) shopkeeper.get(2)); // lastName
        preparedStatement.setString(4, (String) shopkeeper.get(3)); // email
        preparedStatement.setString(5, (String) shopkeeper.get(4)); // adress
        preparedStatement.setString(6, (String) shopkeeper.get(5)); // profilePicture
        preparedStatement.executeUpdate();
        System.out.println("Shopkeeper Profils created successfully.");
    }


}
