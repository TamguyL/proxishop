package org.example.proxishop.model.database.shopkeeper;

import org.example.proxishop.model.entities.shopkeeper.Shopkeeper;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BdCreation {

    /**
     * Creates the database and tables based on the provided classes.
     *
     * @param databaseName the name of the database
     * @param classes      a list of classes to create tables for
     * @param shopkeeper   the shopkeeper object to insert data for
     */
    public void createDatabaseAndTables(String databaseName, List<Class<?>> classes, Shopkeeper shopkeeper) {
        try {
            BdConnection.validateParameters(databaseName, classes);
            Connection connection = BdConnection.establishConnection();
            addBdAndTable(connection, databaseName, classes);
            insertShopkeeperData(connection, shopkeeper);
        } catch (SQLException e) {
            System.err.println("SQL error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }

    /**
     * Creates the database and tables.
     *
     * @param connection   the database connection
     * @param databaseName the name of the database
     * @param classes      a list of classes to create tables for
     * @throws SQLException if a database access error occurs
     */
    private void addBdAndTable(Connection connection, String databaseName, List<Class<?>> classes) throws SQLException {
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

    /**
     * Generates SQL statements to create tables based on the provided classes.
     *
     * @param classes a list of classes to create tables for
     * @return a list of SQL statements
     */
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

    /**
     * Inserts shopkeeper data into the database.
     *
     * @param connection the database connection
     * @param shopkeeper the shopkeeper object to insert data for
     * @throws SQLException if a database access error occurs
     */
    private void insertShopkeeperData(Connection connection, Shopkeeper shopkeeper) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO shopkeeper (siret, firstName, lastName, email, adress, profilePicture) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setDouble(1, shopkeeper.getSiret()); // siret
        preparedStatement.setString(2, shopkeeper.getFirstName()); // firstName
        preparedStatement.setString(3, shopkeeper.getLastName()); // lastName
        preparedStatement.setString(4, shopkeeper.getEmail()); // email
        preparedStatement.setString(5, shopkeeper.getAdress()); // adress
        preparedStatement.setString(6, shopkeeper.getProfilePicture()); // profilePicture
        preparedStatement.executeUpdate();
        System.out.println("Shopkeeper Profils created successfully.");
    }

}
