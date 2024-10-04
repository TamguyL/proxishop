package org.example.proxishop.model.database.shopkeeper;

import org.example.proxishop.model.entities.shopkeeper.Shopkeeper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BdCreation {

    private static final Logger logger = LoggerFactory.getLogger(BdCreation.class);

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Creates the database and tables based on the provided classes.
     *
     * @param website_name the name of the database
     * @param classes      a list of classes to create tables for
     * @param shopkeeper   the shopkeeper object to insert data for
     */
    public void createDatabaseAndTables(String website_name, List<Class<?>> classes, Shopkeeper shopkeeper) {
        try {
            BdConnection.validateParameters(website_name, classes);
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            addBdAndTable(connection, website_name, classes);
            insertShopkeeperData(connection, shopkeeper, website_name);
        } catch (SQLException e) {
            logger.error("SQL error occurred: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: " + e.getMessage(), e);
        }
    }
    /**
     * Creates the database and tables.
     *
     * @param connection   the database connection
     * @param website_name the name of the database
     * @param classes      a list of classes to create tables for
     * @throws SQLException if a database access error occurs
     */
    private void addBdAndTable(Connection connection, String website_name, List<Class<?>> classes) throws SQLException {
        Statement statement = connection.createStatement();

        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + website_name;
        logger.debug("Executing SQL: " + createDatabaseSQL);
        statement.executeUpdate(createDatabaseSQL);
        logger.info("Database " + website_name + " created successfully.");

        // Use the newly created database
        statement.execute("USE " + website_name);

        List<String> createTableSQLs = generateCreateTableSQL(classes);
        for (String createTableSQL : createTableSQLs) {
            logger.debug("Executing SQL: " + createTableSQL);
            statement.executeUpdate(createTableSQL);
        }
        logger.info("All Tables created successfully.");
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
                } else if (field.getType().equals(int.class)) {
                    sql.append("INT");
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
     * @param connection   the database connection
     * @param shopkeeper   the shopkeeper object to insert data for
     * @param website_name the name of the database
     * @throws SQLException if a database access error occurs
     */
    private void insertShopkeeperData(Connection connection, Shopkeeper shopkeeper, String website_name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO " + website_name + ".shopkeeper (siret, firstName, lastName,password, email, adress, profilePicture) VALUES (?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, shopkeeper.getSiret()); // siret
        preparedStatement.setString(2, shopkeeper.getFirstName()); // firstName
        preparedStatement.setString(3, shopkeeper.getLastName());
        preparedStatement.setString(4, shopkeeper.getPassword());// lastName
        preparedStatement.setString(5, shopkeeper.getEmail()); // email
        preparedStatement.setString(6, shopkeeper.getAdress()); // adress
        preparedStatement.setString(7, shopkeeper.getProfilePicture()); // profilePicture
        preparedStatement.executeUpdate();
        System.out.println("Shopkeeper Profile created successfully.");
    }
}